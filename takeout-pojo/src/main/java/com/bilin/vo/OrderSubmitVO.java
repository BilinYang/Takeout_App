package com.bilin.vo;

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
public class OrderSubmitVO implements Serializable {
    // Order ID
    private Long id;

    // Order number
    private String orderNumber;

    // Order amount
    private BigDecimal orderAmount;

    // Order time (when the order was placed)
    private LocalDateTime orderTime;
}
