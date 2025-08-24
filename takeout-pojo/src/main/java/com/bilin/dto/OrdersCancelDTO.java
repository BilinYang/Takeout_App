package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersCancelDTO implements Serializable {

    private Long id;
    // Reason for Order Cancellation
    private String cancelReason;

}
