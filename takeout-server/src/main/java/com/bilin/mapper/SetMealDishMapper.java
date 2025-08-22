package com.bilin.mapper;

import com.bilin.entity.SetMealDish;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SetMealDishMapper {

    public Integer countByDishId(List<Long> dishIds);

    void insertBatch(List<SetMealDish> setMealDishList);

    List<SetMealDish> getDishBySetMealId(Long id);

    void deleteByDishId(Long setMealId);
}
