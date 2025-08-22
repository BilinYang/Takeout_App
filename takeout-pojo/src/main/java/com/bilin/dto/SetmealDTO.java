package com.bilin.dto;

import com.bilin.entity.SetMealDish;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SetMealDTO implements Serializable {

    private Long id;
    private Long categoryId;
    // Set meal name
    private String name;
    private BigDecimal price;
    // 0: Disable sale, 1: Enable sale
    private Integer status;
    private String description;
    private String image;
    // Dishes in the set meal
    private List<SetMealDish> setMealDishes = new ArrayList<>();

}
