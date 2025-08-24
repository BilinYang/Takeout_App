package com.bilin.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    // Number of orders waiting to be accepted
    private Integer toBeConfirmed;

    // Number of orders waiting to be dispatched
    private Integer confirmed;

    // Number of orders currently in delivery
    private Integer deliveryInProgress;
}
