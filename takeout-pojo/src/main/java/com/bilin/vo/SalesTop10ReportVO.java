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
public class SalesTop10ReportVO implements Serializable {

    // Product name list, separated by commas, e.g., Fish-Flavored Shredded Pork, Kung Pao Chicken, Boiled Fish
    private String nameList;

    // Sales count list, separated by commas, e.g., 260,215,200
    private String numberList;

}
