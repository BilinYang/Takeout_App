package com.bilin.mapper;

import org.apache.ibatis.annotations.Select;

public interface SetMealMapper {

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

}
