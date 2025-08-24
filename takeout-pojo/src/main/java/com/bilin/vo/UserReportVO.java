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
public class UserReportVO implements Serializable {

    // Dates, separated by commas, e.g., 2022-10-01,2022-10-02,2022-10-03
    private String dateList;

    // Total users, separated by commas, e.g., 200,210,220
    private String totalUserList;

    // New users, separated by commas, e.g., 20,21,10
    private String newUserList;

}
