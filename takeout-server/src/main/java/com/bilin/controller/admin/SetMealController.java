package com.bilin.controller.admin;

import com.bilin.dto.SetMealDTO;
import com.bilin.dto.SetMealPageQueryDTO;
import com.bilin.result.PageResult;
import com.bilin.result.Result;
import com.bilin.service.SetMealService;
import com.bilin.vo.SetMealVO;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags="Set Meal API (Admin)")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    @CacheEvict(cacheNames="SetMeal", allEntries = true) // evict all caches
    @ApiOperation("Edit Set Meal")
    @PutMapping
    public Result update(@RequestBody SetMealDTO dto) {
        log.info("Update Set Meal: {}", dto);
        setMealService.update(dto);
        return Result.success();
    }

    @ApiOperation("Get Set Meal by ID")
    @GetMapping("/{id}")
    public Result getById(@PathVariable String id){
        log.info("Get Set Meal by ID: {}", id);
        Long longId = Long.parseLong(id);
        SetMealVO setMealVO = setMealService.getById(longId);
        return Result.success(setMealVO);
    }

    @CacheEvict(cacheNames="SetMeal", key="#dto.categoryId")
    @ApiOperation("Add Set Meal")
    @PostMapping
    public Result addSetMeal(@RequestBody SetMealDTO dto) {
        log.info("Add Set Meal: {}", dto);
        setMealService.addSetMeal(dto);
        return Result.success();
    }


    @ApiOperation("Search Set Meals with Pagination")
    @GetMapping("/page")
    public Result<PageResult> page(SetMealPageQueryDTO dto){
        log.info("Search Meal Sets with Pagination: {}", dto);
        PageResult pageResult = setMealService.page(dto);
        return Result.success(pageResult);
    }

    @CacheEvict(cacheNames="SetMeal", allEntries = true)
    @ApiOperation("Batch Delete Set Meals")
    @DeleteMapping
    public Result deleteBatch(@RequestParam List<Long> ids){
        log.info("Delete Set Meals: {}", ids);
        setMealService.deleteBatch(ids);
        return Result.success();
    }

    @CacheEvict(cacheNames="SetMeal", allEntries = true) // evict all caches
    @ApiOperation("Enable/Disable Set Meal Sale Status")
    @PostMapping("/status/{status}")
    public Result enableDisable(@PathVariable Integer status, @RequestParam Long id){
        log.info("Enable/Disable Set Meal Sale Status : {}", status);
        setMealService.enableDisable(status, id);
        return Result.success();
    }

}
