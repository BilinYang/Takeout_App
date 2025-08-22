package com.bilin.service.impl;

import com.bilin.constant.MessageConstant;
import com.bilin.constant.StatusConstant;
import com.bilin.dto.DishDTO;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.entity.Dish;
import com.bilin.entity.DishFlavor;
import com.bilin.exception.DeletionNotAllowedException;
import com.bilin.mapper.DishFlavorMapper;
import com.bilin.mapper.DishMapper;
import com.bilin.mapper.SetMealDishMapper;
import com.bilin.mapper.SetMealDishMapper;
import com.bilin.result.PageResult;
import com.bilin.service.DishService;
import com.bilin.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Transactional // We use @Transactional because we're changing multiple data tables
    public void addDish(DishDTO dto){
        // Get basic parameters of dish and add it to the dish dataset
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.insert(dish);

        log.info("dishId: {}",dish.getId());

        // Get dish flavors and add them to the dish_flavor data table
        List<DishFlavor> dishFlavorList = dto.getFlavors();
        // Add dish id to each of the flavors
        dishFlavorList.forEach(flavor->{
            flavor.setDishId(dish.getId());
        });
        dishFlavorMapper.insertBatch(dishFlavorList);
    }

    @Override
    public PageResult page(DishPageQueryDTO dto){
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<DishVO> page = dishMapper.list(dto);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Transactional
    @Override
    public void delete(List<Long> ids){
        // Determine whether the dish is currently for sale (if so, it cannot be deleted)
        ids.forEach(id->{
            Dish dish = dishMapper.selectById(id);
            if (dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });
        // Determine if the dish is part of meal sets (if so, it cannot be deleted)
        Integer count = setMealDishMapper.countByDishId(ids);
        if (count > 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // Delete the dish in the dish table
        dishMapper.deleteBatch(ids);
        // Delete the dish in the dish_flavor table
        dishFlavorMapper.deleteBatch(ids);
    }

    @Override
    public void startOrStop(Integer status, Long id){
        Dish dish = Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);
    }

    @Override
    public DishVO getById(Long id) {
        DishVO dishVO = new DishVO();
        // Search dish information in dish table
        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish, dishVO);

        // Search dish flavor information in dish_flavor table
        List<DishFlavor> flavors = dishFlavorMapper.selectByDishId(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Transactional
    @Override
    public void update(DishDTO dto) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        // Edit dish information (information in dish table)
        dishMapper.update(dish);
        // Edit dish flavor information (information in dish_flavor table)
        dishFlavorMapper.deleteByDishId(dto.getId());

        List<DishFlavor> flavors = dto.getFlavors();
        // we need to write 'flavors.size() > 0' because otherwise, even if there are no flavors,
        // dishFlavorMapp.insetBatch will still execute, causing a SQL grammar error since there is nothing to iterate over:
        // the SQL will only be 'insert into dish_flavor values', which is grammatically incorrect
        if (flavors != null && flavors.size() > 0) {
            // set the dish id of each flavor
            flavors.forEach(flavor->{
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }

    }
}
