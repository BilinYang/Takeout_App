package com.bilin.mapper;

import com.bilin.annotation.AutoFill;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.entity.Dish;
import com.bilin.enumeration.OperationType;
import com.bilin.vo.DishVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DishMapper {

    @Select("select count(id) from  dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @AutoFill(OperationType.INSERT)
    @Options(useGeneratedKeys=true, keyProperty="id")
    @Insert("insert into dish values (null, #{name}, #{categoryId}, #{price}, #{image}, #{description}, " +
            "#{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void insert(Dish dish);

    Page<DishVO> list(DishPageQueryDTO dto);

    @Select("select * from dish where id=#{id}")
    Dish selectById(Long id);

    void deleteBatch(List<Long> ids);

    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    @Select("select status from dish where id=#{dishId}")
    Integer getStatusById(Long dishId);
}
