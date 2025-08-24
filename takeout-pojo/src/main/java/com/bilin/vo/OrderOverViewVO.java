package com.bilin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverViewVO implements Serializable {
    // Number of orders waiting to be accepted
    private Integer waitingOrders;

    // Number of orders waiting to be delivered
    private Integer deliveredOrders;

    // Number of completed orders
    private Integer completedOrders;

    // Number of cancelled orders
    private Integer cancelledOrders;

    // Total number of all orders
    private Integer allOrders;
}
