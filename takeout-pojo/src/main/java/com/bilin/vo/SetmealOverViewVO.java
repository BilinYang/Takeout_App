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
public class SetMealOverViewVO implements Serializable {
    // Number of items currently on sale
    private Integer sold;

    // Number of items discontinued (no longer for sale)
    private Integer discontinued;
}
