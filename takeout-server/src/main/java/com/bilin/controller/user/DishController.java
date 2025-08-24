package com.bilin.controller.user;

import com.bilin.constant.StatusConstant;
import com.bilin.entity.Dish;
import com.bilin.result.Result;
import com.bilin.service.DishService;
import com.bilin.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Api(tags = "Show Dishes (User)")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("Search Dish by ID")
    public Result<List<DishVO>> list(Long categoryId) {

        // Caching (optimization)
        // Before searching in MySQL database, check if the data is in the redis cache
        List<DishVO> dishVOList = (List<DishVO>) redisTemplate.opsForValue().get("dish_" + categoryId);
        // If there is a cache, immediately return the cached data
        String key = "dish_" + categoryId;
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(list != null && list.size() > 0){
            return Result.success(list);
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        list = dishService.listWithFlavor(dish);

        // Cache data into redis
        redisTemplate.opsForValue().set(key, list);

        return Result.success(list);
    }

}
