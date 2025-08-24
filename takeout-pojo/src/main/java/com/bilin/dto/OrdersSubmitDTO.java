package com.bilin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    private Long addressBookId;
    private int payMethod;
    // Comment made by orderer regarding their order
    private String remark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    // Delivery Status  1.Deliver right now  0.Schedule delivery time
    private Integer deliveryStatus;
    private Integer tablewareNumber;
    // Tableware number status 1.Deliver according to number of dishes 0.Choose number of sets of tableware
    private Integer tablewareStatus;
    // Packaging cost
    private Integer packAmount;
    // Total cost
    private BigDecimal amount;
}
