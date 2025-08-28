package com.bilin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    // Order status 1.Unpaid 2.Unreceived order 3.Received order 4.Delivering 5.Completed 6.Cancelled 7.Refunded
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    // Payment status 0.Unpaid 1.Paid 2.Refunded
    public static final Integer UNPAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    // Order number
    private String number;

    // Order status 1.Unpaid 2.Unreceived order 3.Received order 4.Delivering 5.Completed 6.Cancelled 7.Refunded
    private Integer status;
    private Long userId;
    private Long addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;

    // Payment method 1.WechatPay 2.AliPay
    private Integer payMethod;

    // Payment status 0.Unpaid 1.Paid 2.Refunded
    private Integer payStatus;

    // Amount received
    private BigDecimal amount;

    // Note the orderer made
    private String remark;
    private String userName;
    private String phone;
    private String address;
    private String consignee;
    // Reason for the orderer cancelling their order
    private String cancelReason;
    // Reason for the restaurant to reject the order
    private String rejectionReason;
    // Order cancel time
    private LocalDateTime cancelTime;
    private LocalDateTime estimatedDeliveryTime;
    // Delivery status 1.Deliver immediately 0.Select divery time
    private Integer deliveryStatus;
    // Delivered time
    private LocalDateTime deliveryTime;
    // Packaging cost
    private int packAmount;
    // Number of sets of tableware
    private int tablewareNumber;
    // Tableware number status 1.Deliver according to number of dishes 0.Choose number of sets of tableware
    private Integer tablewareStatus;
}
