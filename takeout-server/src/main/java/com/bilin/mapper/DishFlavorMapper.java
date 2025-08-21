package com.bilin.mapper;

import com.bilin.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface DishFlavorMapper {

    void insertBatch(List<DishFlavor> dishFlavorList);
}
