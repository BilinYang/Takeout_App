package com.bilin.vo;

import com.bilin.entity.DishFlavor;
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
public class DishVO implements Serializable {

    private Long id;
    private String name;
    private Long categoryId;
    private BigDecimal price;
    private String image;
    private String description;
    // 0: Disable sale, 1: Enable sale
    private Integer status;
    private LocalDateTime updateTime;
    private String categoryName;
    private List<DishFlavor> flavors = new ArrayList<>();
    //private Integer copies;
}
