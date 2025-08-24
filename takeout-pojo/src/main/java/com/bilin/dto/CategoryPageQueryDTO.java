package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryPageQueryDTO implements Serializable {

    private int page;
    private int pageSize;
    private String name;
    private Integer type;

}
