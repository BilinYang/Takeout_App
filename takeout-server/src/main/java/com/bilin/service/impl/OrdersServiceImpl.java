package com.bilin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bilin.constant.MessageConstant;
import com.bilin.context.BaseContext;
import com.bilin.dto.*;
import com.bilin.entity.*;
import com.bilin.exception.OrderBusinessException;
import com.bilin.mapper.*;
import com.bilin.result.PageResult;
import com.bilin.service.OrdersService;
import com.bilin.vo.OrderPaymentVO;
import com.bilin.vo.OrderStatisticsVO;
import com.bilin.vo.OrderSubmitVO;
import com.bilin.vo.OrderVO;
import com.bilin.websocket.WebSocketServer;
import com.bilin.utils.WeChatPayUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private WeChatPayUtil weChatPayUtil;
    @Autowired
    private DishMapper dishMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderSubmitVO submit(OrdersSubmitDTO dto) {
        AddressBook addressBook = addressBookMapper.getById(dto.getAddressBookId());
        if (addressBook==null){
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userId = BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);
        if (user==null){
            throw new OrderBusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        Orders orders = new  Orders();
        BeanUtils.copyProperties(dto, orders);

        // Fill in missing value for orders instance
        orders.setNumber(System.currentTimeMillis()+"");
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(BaseContext.getCurrentId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UNPAID);
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserName(user.getName());
        ordersMapper.insert(orders);
        log.info("Order ID: {}", orders.getId());

        // Create order details data and save them to the order_detail data table
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(userId);
        if (shoppingCartList==null && shoppingCartList.size()==0){
            throw new  OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }
        log.debug("Shopping cart list: {}", JSON.toJSONString(shoppingCartList));
        shoppingCartList.forEach(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart, orderDetail, "id");
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        });
        // Batch insert order details into order_detail data table
        log.debug("Order detail list: {}", orderDetailList.toString());
        orderDetailMapper.insertBatch(orderDetailList);

        // Clear shopping cart
        shoppingCartMapper.deleteByUserId(userId);

        // Construct OrderSubmitVO instance and return
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
    }

    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // Note: we commented so much code out because we are only SIMULATING a WeChat payment
//        // Login ID of current user
//        Long userId = BaseContext.getCurrentId();
//        User user = userMapper.selectById(userId);
//
////        Call the WeChat Pay API to generate a prepay transaction order
////        JSONObject jsonObject = weChatPayUtil.pay(
////                ordersPaymentDTO.getOrderNumber(), // Merchant order number
////                new BigDecimal(0.01),              // Payment amount in yuan
////                "Cangqiong Takeout Order",         // Product description
////                user.getOpenid()                   // WeChat user's OpenID
////        );
//
//        // Construct empty JSON to skip over WeChat Pay steps
//        JSONObject jsonObject = new JSONObject();
//
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("This ordered has already been paid.");
//        }
//
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));
//
//        return vo;


        // Simulate payment success (comment out if we are implementing real payments)
        paySuccess(ordersPaymentDTO.getOrderNumber());
        return new OrderPaymentVO();
    }

    // Modify payment status in orders table once payment is successful
    @Override
    public void paySuccess(String outTradeNo) {

        // Search for order according to order number
        Orders ordersDB = ordersMapper.getByNumber(outTradeNo);

        // Update order status, payment status, and checkout time according to order ID
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        ordersMapper.update(orders);

        // Use webSocket to send notification to customer server (type, orderId, content)
        Map map = new HashMap();
        map.put("type",1); // 1.New Order notification，2.Customer wants restaurant to expedite their delivery
        map.put("orderId",ordersDB.getId());
        map.put("content","Order Number：" + outTradeNo);

        String json = JSON.toJSONString(map);
        webSocketServer.sendToAllClient(json);
    }

    @Override
    public void cancelOrder(OrdersCancelDTO dto) throws Exception {
        Orders testOrder = ordersMapper.getById(dto.getId());
        if (testOrder.getPayStatus().equals(Orders.PAID)) { // user already paid, need to refund them
            String refund = weChatPayUtil.refund(
                    testOrder.getNumber(),
                    testOrder.getNumber(),
                    new BigDecimal(0.01),
                    new BigDecimal(0.01));
            log.info("Refund request：{}", refund);
        }

        Orders order = new Orders();
        order.setId(dto.getId());
        order.setStatus(Orders.CANCELLED);
        order.setCancelReason(dto.getCancelReason());
        order.setOrderTime(LocalDateTime.now());
        ordersMapper.update(order);
    }

    @Override
    public OrderStatisticsVO getStats() {
        Integer toBeConfirmed = ordersMapper.countByStatus(Orders.TO_BE_CONFIRMED);
        Integer confirmed = ordersMapper.countByStatus(Orders.CONFIRMED);
        Integer deliveryInProgress = ordersMapper.countByStatus(Orders.DELIVERY_IN_PROGRESS);

        OrderStatisticsVO vo = new OrderStatisticsVO();
        vo.setConfirmed(confirmed);
        vo.setToBeConfirmed(toBeConfirmed);
        vo.setDeliveryInProgress(deliveryInProgress);
        return vo;
    }

    @Override
    public void completeOrder(Long id) {
        Orders orderDB = ordersMapper.getById(id);

        if (orderDB == null || !orderDB.getStatus().equals(Orders.DELIVERY_IN_PROGRESS)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders order = new Orders();
        order.setStatus(Orders.COMPLETED);
        order.setId(orderDB.getId());
        order.setDeliveryTime(LocalDateTime.now());

        ordersMapper.update(order);
    }

    @Override
    public void rejectOrder(OrdersRejectionDTO dto) throws Exception {
        Orders orderDB = ordersMapper.getById(dto.getId());

        log.info("OrderDB: {}", orderDB);

        if (orderDB == null || !orderDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Integer payStatus = orderDB.getPayStatus();
        if (payStatus == Orders.PAID) {
            String refund = weChatPayUtil.refund(
                    orderDB.getNumber(),
                    orderDB.getNumber(),
                    new BigDecimal(0.01),
                    new BigDecimal(0.01));
            log.info("Refund request: {}", refund);
        }

        Orders order = new Orders();
        order.setId(dto.getId());
        order.setStatus(Orders.PAID);
        order.setRejectionReason(dto.getRejectionReason());
        order.setCancelTime(LocalDateTime.now());

        ordersMapper.update(order);
    }

    @Override
    public void confirmOrder(OrdersConfirmDTO dto) {
        Orders order =  new Orders();
        order.setId(dto.getId());
        order.setStatus(Orders.CONFIRMED);
        ordersMapper.update(order);
    }

    @Override
    public OrderVO getOrderDetails(Long id) {
        Orders order = ordersMapper.getById(id);
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailList(order.getId());
        vo.setOrderDetailList(orderDetailList);
        String orderDishes = getOrderDishesStr(order);
        vo.setOrderDishes(orderDishes);
        return vo;
    }

    @Override
    public void deliverOrder(Long id) {
        Orders orderDB = ordersMapper.getById(id);

        if (orderDB == null || !orderDB.getStatus().equals(Orders.CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders order = new Orders();
        order.setId(orderDB.getId());
        order.setStatus(Orders.DELIVERY_IN_PROGRESS);
        ordersMapper.update(order);
    }

    @Override
    public PageResult conditionSearch(OrdersPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<Orders> page = ordersMapper.pageQuery(dto);
        log.info("page: {}", page);
        List<OrderVO> orderVOList = getOrderVOList(page);
        return new PageResult(page.getTotal(), orderVOList);
    }

    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Orders order : page) {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);

            String orderDishes = getOrderDishesStr(order);
            orderVO.setOrderDishes(orderDishes);

            List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailList(order.getId());
            orderVO.setOrderDetailList(orderDetailList);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    private String getOrderDishesStr(Orders order) {
        List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailList(order.getId());
        List<String> orderDishList = orderDetailList.stream()
                .map(x -> {
                    String orderDish = x.getName() + "*" + x.getNumber() + ";" + " ";
                    return orderDish;
                }).collect(Collectors.toList());
        return String.join("", orderDishList);
    }

    @Override
    public void sendExpediteOrderRequest(Long id) {
        Orders orderDB = ordersMapper.getById(id);
        if (orderDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        Map map = new HashMap();
        map.put("type", 2);
        map.put("orderId", id);
        map.put("content", "Order number: " + orderDB.getNumber());
        webSocketServer.sendToAllClient(JSON.toJSONString(map));
    }

    @Override
    public void makeAnotherOrder(Long orderId) {
        Long userId = BaseContext.getCurrentId();

        List<OrderDetail> orderDetailList = orderDetailMapper.getOrderDetailList(orderId);
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart cart = new ShoppingCart();
            BeanUtils.copyProperties(x, cart, "id");
            cart.setUserId(userId);
            cart.setCreateTime(LocalDateTime.now());
            return cart;
        }).collect(Collectors.toList());

        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    @Override
    public PageResult userGetOrderHistory(int page, int pageSize, int status) {
        PageHelper.startPage(page, pageSize);
        Long userId = BaseContext.getCurrentId();

        OrdersPageQueryDTO dto = new OrdersPageQueryDTO();
        dto.setUserId(userId);
        dto.setStatus(status);
        Page<Orders> results = ordersMapper.pageQuery(dto);
        log.info("User order history query results: {}", results);
        List<OrderVO> orderVOList = getOrderVOList(results);
        return new PageResult(results.getTotal(), orderVOList);
    }

    @Override
    public void userCancelOrder(Long id) throws Exception {
        Orders orderDB = ordersMapper.getById(id);
        if (orderDB == null){
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // Order Status 1: Pending Payment, 2: Awaiting Acceptance/Confirmation, 3: Confirmed, 4: Delivering, 5: Completed, 6: Cancelled, 7: Refunded
        if (orderDB.getStatus() > 2){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders order = new Orders();
        order.setId(orderDB.getId());

        if (orderDB.getStatus() == Orders.TO_BE_CONFIRMED){ // If the user has already paid, they need to be refunded
            weChatPayUtil.refund(
                orderDB.getNumber(),
                orderDB.getNumber(),
                new BigDecimal(0.01),
                new BigDecimal(0.01));
            order.setPayStatus(Orders.REFUND);
        }

        order.setStatus(Orders.CANCELLED);
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason("User cancelled order.");
        ordersMapper.update(order);
    }


}
