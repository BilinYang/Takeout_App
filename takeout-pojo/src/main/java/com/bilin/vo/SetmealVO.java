package com.bilin.vo;

import com.bilin.entity.SetMealDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetMealVO implements Serializable {

    private Long id;
    private Long categoryId;
    private String name;
    private BigDecimal price;
    // 0: Disable sale, 1: Enable sale
    private Integer status;
    private String description;
    private String image;
    private LocalDateTime updateTime;
    private String categoryName;
    private List<SetMealDish> setmealDishes = new ArrayList<>();
}
