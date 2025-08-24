package com.bilin.controller.user;

import com.bilin.constant.StatusConstant;
import com.bilin.entity.SetMeal;
import com.bilin.result.Result;
import com.bilin.service.SetMealService;
import com.bilin.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Api(tags = "Set Meal API (User)")
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    @Cacheable(cacheNames="SetMeal", key="#categoryId")
    @GetMapping("/list")
    @ApiOperation("Query by Category ID")
    public Result<List<SetMeal>> list(Long categoryId) {
        SetMeal setmeal = new SetMeal();
        setmeal.setCategoryId(categoryId);
        setmeal.setStatus(StatusConstant.ENABLE);

        List<SetMeal> list = setMealService.list(setmeal);
        return Result.success(list);
    }

    @GetMapping("/dish/{id}")
    @ApiOperation("Query by Dish ID")
    public Result<List<DishItemVO>> dishList(@PathVariable("id") Long id) {
        List<DishItemVO> list = setMealService.getDishItemById(id);
        return Result.success(list);
    }
}
