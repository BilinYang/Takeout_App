package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    private Long id;
    // 1 Dish 2 Set Meal
    private Integer type;
    private String name;
    private Integer sort;

}
