package com.bilin.service.impl;

import com.bilin.constant.StatusConstant;
import com.bilin.entity.Orders;
import com.bilin.mapper.DishMapper;
import com.bilin.mapper.OrdersMapper;
import com.bilin.mapper.SetMealMapper;
import com.bilin.mapper.UserMapper;
import com.bilin.service.WorkspaceService;
import com.bilin.vo.BusinessDataVO;
import com.bilin.vo.DishOverViewVO;
import com.bilin.vo.OrderOverViewVO;
import com.bilin.vo.SetMealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public  class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;


    // Get business data statistics based on a time period
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /**
         * Turnover: total amount of completed orders for the day
         * Valid orders: number of completed orders for the day
         * Order completion rate: valid orders / total orders
         * Average unit price: turnover / valid orders
         * New users: number of new users added that day
         */

        Map map = new HashMap();
        map.put("beginTime",begin);
        map.put("endTime",end);

        //Query total number of orders
        Integer totalOrderCount = ordersMapper.countByMap(map);

        map.put("status", Orders.COMPLETED);
        //Turnover
        Double turnover = ordersMapper.sumByMap(map);
        turnover = turnover == null? 0.0 : turnover;

        //Number of valid orders
        Integer validOrderCount = ordersMapper.countByMap(map);

        Double unitPrice = 0.0;

        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //Order completion rate
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //Average unit price
            unitPrice = turnover / validOrderCount;
        }

        //Number of new users
        Integer newUsers = userMapper.countByMap(map);

        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }


    // Query order management data
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("beginTime", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", Orders.TO_BE_CONFIRMED);

        //Pending orders
        Integer waitingOrders = ordersMapper.countByMap(map);

        //To be delivered
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = ordersMapper.countByMap(map);

        //Completed
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = ordersMapper.countByMap(map);

        //Cancelled
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = ordersMapper.countByMap(map);

        //All orders
        map.put("status", null);
        Integer allOrders = ordersMapper.countByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }


    // Query dish overview
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    // Query set meal overview
    public SetMealOverViewVO getSetMealOverView() {
        Map map = new HashMap();
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setMealMapper.countByMap(map);

        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setMealMapper.countByMap(map);

        return SetMealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }
}