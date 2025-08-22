package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetMealPageQueryDTO implements Serializable {

    private int page;
    private int pageSize;
    private String name;
    private Integer categoryId;
    // 0: Disable sale, 1: Enable sale
    private Integer status;
}
