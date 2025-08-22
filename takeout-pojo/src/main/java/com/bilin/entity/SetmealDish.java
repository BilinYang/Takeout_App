package com.bilin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetMealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long setMealId;
    private Long dishId;
    private String name;
    private BigDecimal price;
    // the number of this dish in the set meal
    private Integer copies;
}
