package com.bilin.controller.admin;

import com.bilin.dto.DishDTO;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.result.PageResult;
import com.bilin.result.Result;
import com.bilin.service.DishService;
import com.bilin.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "Dish API (Admin)")
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @ApiOperation("Add Dish")
    @PostMapping
    public Result addDish(@RequestBody DishDTO dto) {
        log.info("Add dish: {}", dto);
        dishService.addDish(dto);
        return Result.success();
    }

    @ApiOperation("Search for Dishes with Pagination")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dto){
        log.info("Search for Dishes with Pagination: {}", dto);
        PageResult pageResult = dishService.page(dto);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
        log.info("Delete Dishes with ids: {}", ids);
        dishService.delete(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("Enable/Disable Category")
    public Result<String> startOrStop(@PathVariable("status") Integer status, Long id){
        dishService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get Dish by ID")
    public Result getById(@PathVariable("id") Long id){
        log.info("Get Dish with id: {}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }

    @ApiOperation("Edit Dish")
    @PutMapping
    public Result update(@RequestBody DishDTO dto){
        log.info("Edit Dish Information: {}", dto);
        dishService.update(dto);
        return Result.success();
    }
}
