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
public class OrderReportVO implements Serializable {

    // Dates, separated by commas, e.g., 2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    // Daily order counts, separated by commas, e.g., 260,210,215
    private String orderCountList;

    // Daily valid order counts, separated by commas, e.g., 20,21,10
    private String validOrderCountList;

    // Total number of orders
    private Integer totalOrderCount;

    // Number of valid orders
    private Integer validOrderCount;

    // Order completion rate
    private Double orderCompletionRate;

}
