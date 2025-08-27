package com.bilin.task;

import com.bilin.constant.MessageConstant;
import com.bilin.entity.Orders;
import com.bilin.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

// OrderTask Class:
// 1. Check every minute to: Cancel orders that remain unpaid for after some time
// 2. Check every day at 4am to: Change the status of any orders that were created at least 2 hours ago
//    with status 'Delivering' to 'Completed'
@Slf4j
@Component
public class OrderTask {

    @Autowired
    private OrdersMapper ordersMapper;

    // Check every minute to: Cancel orders that remain unpaid 15 minutes after it was made
    @Scheduled(cron="0 * *  * * ?") // Execute every minute
    public void processPaymentTimeout() {
        // Get all orders that remain unpaid for (status = 1) 15 minutes after it was made
        LocalDateTime thresholdTime = LocalDateTime.now().minusMinutes(15);
        List<Orders> ordersList = ordersMapper.selectByStatusAndOrderTime(Orders.PENDING_PAYMENT, thresholdTime);

        // Set the orders to have a status of 'Cancelled' (status = 6)
        if (ordersList != null && ordersList.size() > 0) {
            ordersList.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason(MessageConstant.ORDER_NOT_PAID_FOR);
                order.setCancelTime(LocalDateTime.now());
                ordersMapper.update(order);
            });
            // log.info("Automatically cancelled unpaid-for orders");
        }
    }

    // Check every day at 4am to: Change the status of any orders that were created at least 2 hours
    @Scheduled(cron="0 0 4 * * ?")
    public void setStatusToCompleted() {
        // Get all orders that have status: delivering and that were created at least 2 hours ago
        LocalDateTime thresholdTime = LocalDateTime.now().minusHours(2);
        List<Orders> ordersList = ordersMapper.selectByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, thresholdTime);
        if (ordersList != null && ordersList.size() > 0) {
            ordersList.forEach(order -> {
                order.setStatus(Orders.COMPLETED);
                order.setDeliveryTime(LocalDateTime.now());
                ordersMapper.update(order);
            });
        }
        log.info("Automatically completed orders with status 'Delivering'");
    }
}
