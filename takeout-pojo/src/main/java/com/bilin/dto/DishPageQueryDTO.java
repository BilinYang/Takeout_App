package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO implements Serializable {

    private int page;
    private int pageSize;
    private String name;
    private Integer categoryId;
    // Status: 0 = Disabled, 1 = Available
    private Integer status;

}
