package com.bilin.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SetMealDishMapper {

    public Integer countByDishId(List<Long> dishIds);
}
