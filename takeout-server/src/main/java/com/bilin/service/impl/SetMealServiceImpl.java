package com.bilin.service.impl;

import com.bilin.annotation.AutoFill;
import com.bilin.constant.MessageConstant;
import com.bilin.constant.StatusConstant;
import com.bilin.dto.SetMealDTO;
import com.bilin.dto.SetMealPageQueryDTO;
import com.bilin.entity.SetMeal;
import com.bilin.entity.SetMealDish;
import com.bilin.enumeration.OperationType;
import com.bilin.exception.DeletionNotAllowedException;
import com.bilin.exception.SetmealEnableFailedException;
import com.bilin.mapper.CategoryMapper;
import com.bilin.mapper.DishMapper;
import com.bilin.mapper.SetMealDishMapper;
import com.bilin.mapper.SetMealMapper;
import com.bilin.result.PageResult;
import com.bilin.service.SetMealService;
import com.bilin.vo.DishItemVO;
import com.bilin.vo.SetMealVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;

    @Override
    @Transactional
    public void update(SetMealDTO dto){
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(dto, setMeal);
        setMealMapper.update(setMeal);

        Long setMealId = setMeal.getId();
        List<SetMealDish> setMealDishes = dto.getSetMealDishes();
        setMealDishMapper.deleteByDishId(dto.getId());
        setMealDishes.forEach(setMealDish -> {
            Integer status = dishMapper.getStatusById(setMealDish.getDishId());
            if (status == StatusConstant.DISABLE){
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            };
            setMealDish.setSetmealId(setMealId);
        });
        setMealDishMapper.insertBatch(setMealDishes);
    }

    @Transactional
    @Override
    public SetMealVO getById(Long id) {
        SetMealVO setMealVO = new SetMealVO();
        SetMeal setMeal = setMealMapper.getById(id);
        BeanUtils.copyProperties(setMeal, setMealVO);

        Long categoryId = setMeal.getCategoryId();
        String categoryName = categoryMapper.getCategoryNameById(categoryId);
        setMealVO.setCategoryName(categoryName);

        List<SetMealDish> setMealDishList = setMealDishMapper.getDishBySetMealId(id);
        setMealVO.setSetmealDishes(setMealDishList);

        return setMealVO;
    }

    @Override
    @Transactional
    public void addSetMeal(SetMealDTO dto){
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(dto, setMeal);
        setMealMapper.insert(setMeal);

        List<SetMealDish> setMealDishList= dto.getSetMealDishes();

        Long setMealId = setMeal.getId();
        log.info("Set meal id:{}", setMealId);

        setMealDishList.forEach(setMealDish->{
            Integer status = dishMapper.getStatusById(setMealDish.getDishId());
            if (status == StatusConstant.DISABLE){
                throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
            setMealDish.setSetmealId(setMealId);
        });
        setMealDishMapper.insertBatch(setMealDishList);
    }

    @Override
    public PageResult page(SetMealPageQueryDTO dto){
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<SetMeal> page = setMealMapper.pageQuery(dto);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        ids.forEach(id -> {
            SetMeal setMeal = setMealMapper.getById(id);
            if (setMeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
            setMealDishMapper.deleteByDishId(id);
        });
        setMealMapper.deleteBatch(ids);
    }

    @Override
    public void enableDisable(Integer status, Long id) {
        setMealMapper.enableDisable(status, id);
    }

    @Override
    public List<SetMeal> list(SetMeal setMeal) {
        List<SetMeal> list = setMealMapper.list(setMeal);
        return list;
    }

    @Override
    public List<DishItemVO> getDishItemById(Long id) {
        return setMealMapper.getDishItemBySetmealId(id);
    }

}
