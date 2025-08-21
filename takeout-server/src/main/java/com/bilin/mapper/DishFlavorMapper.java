package com.bilin.mapper;

import com.bilin.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface DishFlavorMapper {

    List<DishFlavor> selectByDishId(Long dishId);

    void insertBatch(List<DishFlavor> dishFlavorList);


    void deleteBatch(List<Long> ids);

    void deleteByDishId(Long dishId);
}
