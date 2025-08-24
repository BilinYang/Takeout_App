package com.bilin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据概览
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO implements Serializable {

    private Double turnover;
    private Integer validOrderCount;
    private Double orderCompletionRate;
    // Average amount paid per customer
    private Double unitPrice;//平均客单价
    // Number of new users
    private Integer newUsers;

}
