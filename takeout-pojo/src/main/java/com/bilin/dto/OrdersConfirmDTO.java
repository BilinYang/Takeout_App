package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {

    private Long id;
    // Order status 1.Unpaid 2.Unreceived order 3.Received order 4.Delivering 5.Completed 6.Cancelled 7.Refunded
    private Integer status;

}
