package com.bilin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.bilin.constant.MessageConstant;
import com.bilin.constant.StatusConstant;
import com.bilin.dto.CategoryDTO;
import com.bilin.dto.CategoryPageQueryDTO;
import com.bilin.entity.Category;
import com.bilin.exception.DeletionNotAllowedException;
import com.bilin.mapper.CategoryMapper;
import com.bilin.mapper.DishMapper;
import com.bilin.mapper.SetMealMapper;
import com.bilin.result.PageResult;
import com.bilin.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setmealMapper;

    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        category.setStatus(StatusConstant.DISABLE);

        // The following 4 lines are unnecessary because we already used logic for auto-population based on common fields
        // category.setCreateTime(LocalDateTime.now());
        // category.setUpdateTime(LocalDateTime.now());
        // category.setCreateUser(BaseContext.getCurrentId());
        // category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }

    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    public void deleteById(Long id) {
        // Check whether the current category is associated with any dishes; if yes, throw a business exception
        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0){
            // The current category has dishes, so it cannot be deleted
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        // Check whether the current category is associated with any set meals; if yes, throw a business exception
        count = setmealMapper.countByCategoryId(id);
        if(count > 0){
            // The current category has set meals, so it cannot be deleted
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        // Delete category data
        categoryMapper.deleteById(id);
    }

    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        // Set update time and updater
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }

    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.update(category);
    }

    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
