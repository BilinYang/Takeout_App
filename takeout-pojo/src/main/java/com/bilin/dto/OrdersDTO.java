package com.bilin.dto;

import com.bilin.entity.OrderDetail;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO implements Serializable {

    private Long id;

    // Order number
    private String number;
    // Order status 1.Unpaid 2.Unreceived order 3.Received order 4.Delivering 5.Completed 6.Cancelled 7.Refunded
    private Integer status;
    // ID of user who made the order
    private Long userId;
    // Address ID
    private Long addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    // Payment method 1.WechatPay，2.AliPay
    private Integer payMethod;
    // Actual paid amount (not counting discounts)
    private BigDecimal amount;
    // Notes made by orderer regarding their order
    private String remark;
    private String userName;
    private String phone;
    private String address;
    // Orderer
    private String consignee;
    private List<OrderDetail> orderDetails;

}
