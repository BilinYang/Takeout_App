package com.bilin.controller.user;

import com.bilin.dto.ShoppingCartDTO;
import com.bilin.entity.ShoppingCart;
import com.bilin.result.Result;
import com.bilin.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags="Shopping Cart API")
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("Add to Shopping Cart")
    @PostMapping("/add")
    public Result addToCart(@RequestBody ShoppingCartDTO dto){
        log.info("Add to shopping cart: {}", dto);
        shoppingCartService.addToCart(dto);
        return Result.success();
    }

    @ApiOperation("Show Shopping Cart")
    @GetMapping("/list")
    public Result list(){
        log.info("Show Shopping Cart");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    @ApiOperation("Clear Shopping Cart")
    @DeleteMapping("/clean")
    public Result clean(){
        log.info("Clear Shopping Cart");
        shoppingCartService.clean();
        return Result.success();
    }


    @ApiOperation("Remove Item from Shopping Cart")
    @PostMapping("/sub")
    public Result removeItem(@RequestBody ShoppingCartDTO dto){
        log.info("Remove Item from Shopping Cart: {}", dto);
        shoppingCartService.removeItem(dto);
        return Result.success();
    }


}
