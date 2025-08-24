package com.bilin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    private String name;
    private int page;
    private int pageSize;

}
