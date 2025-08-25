package com.bilin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bilin.constant.MessageConstant;
import com.bilin.context.BaseContext;
import com.bilin.dto.OrdersPaymentDTO;
import com.bilin.dto.OrdersSubmitDTO;
import com.bilin.entity.*;
import com.bilin.exception.OrderBusinessException;
import com.bilin.mapper.*;
import com.bilin.service.OrdersService;
import com.bilin.vo.OrderPaymentVO;
import com.bilin.vo.OrderSubmitVO;
import com.bilin.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private OrdersService ordersService;


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

        // Simulate payment success
        paySuccess(ordersPaymentDTO.getOrderNumber());
        return new OrderPaymentVO();
    }

    // Modify payment status in orders table once payment is successful
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


}
