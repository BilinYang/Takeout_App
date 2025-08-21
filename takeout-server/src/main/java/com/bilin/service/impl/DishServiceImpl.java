package com.bilin.service.impl;

import com.bilin.dto.DishDTO;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.entity.Dish;
import com.bilin.entity.DishFlavor;
import com.bilin.mapper.DishFlavorMapper;
import com.bilin.mapper.DishMapper;
import com.bilin.result.PageResult;
import com.bilin.service.DishService;
import com.bilin.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional // We use @Transactional because we're changing multiple datasets
    public void addDish(DishDTO dto){
        // Get basic parameters of dish and add it to the dish dataset
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dishMapper.insert(dish);

        log.info("dishId:{}",dish.getId());

        // Get dish flavors and add them to the dish_flavors dataset
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

}
