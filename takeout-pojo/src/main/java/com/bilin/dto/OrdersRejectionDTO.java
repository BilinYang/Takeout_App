package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersRejectionDTO implements Serializable {

    private Long id;

    // Reason for rejecting order
    private String rejectionReason;

}
