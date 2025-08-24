package com.bilin.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishOverViewVO implements Serializable {
    // Number of items sold
    private Integer sold;

    // Number of items discontinued
    private Integer discontinued;
}
