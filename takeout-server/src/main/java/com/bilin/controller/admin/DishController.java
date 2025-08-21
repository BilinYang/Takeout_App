package com.bilin.controller.admin;

import com.bilin.dto.DishDTO;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.result.PageResult;
import com.bilin.result.Result;
import com.bilin.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RestController
@Api(tags = "Dish API")
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

}
