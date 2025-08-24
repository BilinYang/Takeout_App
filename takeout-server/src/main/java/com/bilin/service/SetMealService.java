package com.bilin.service;

import com.bilin.dto.SetMealDTO;
import com.bilin.dto.SetMealPageQueryDTO;
import com.bilin.entity.SetMeal;
import com.bilin.result.PageResult;
import com.bilin.vo.DishItemVO;
import com.bilin.vo.SetMealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetMealService {
    void addSetMeal(SetMealDTO dto);

    void update(SetMealDTO dto);

    SetMealVO getById(Long id);

    PageResult page(SetMealPageQueryDTO dto);

    void deleteBatch(List<Long> ids);

    void enableDisable(Integer status, Long id);

    List<SetMeal> list(SetMeal setMeal);

    List<DishItemVO> getDishItemById(Long id);

}
