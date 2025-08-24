package com.bilin.mapper;

import com.bilin.annotation.AutoFill;
import com.bilin.dto.SetMealDTO;
import com.bilin.dto.SetMealPageQueryDTO;
import com.bilin.entity.SetMeal;
import com.bilin.entity.SetMealDish;
import com.bilin.enumeration.OperationType;
import com.bilin.vo.DishItemVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetMealMapper {

    @AutoFill(OperationType.INSERT)
    void insert(SetMeal setMeal);

    Integer countByCategoryId(Long categoryId);

    SetMeal getById(Long id);

    @AutoFill(OperationType.UPDATE)
    void update(SetMeal setMeal);

    Page<SetMeal> pageQuery(SetMealPageQueryDTO dto);

    void deleteBatch(List<Long> ids);

    void enableDisable(Integer status, Long id);

    List<SetMeal> list(SetMeal setMeal);

    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    Integer countByMap(Map map);
}
