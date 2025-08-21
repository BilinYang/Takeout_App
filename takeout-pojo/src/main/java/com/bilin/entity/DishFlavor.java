package com.bilin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long dishId;
    // flavor name
    private String name;
    // specifics for the current flavor (e.g. no sugar, less sugar, regular sugar, etc.)
    private String value;

}
