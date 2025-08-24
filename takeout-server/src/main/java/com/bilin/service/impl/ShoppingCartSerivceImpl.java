package com.bilin.service.impl;

import com.bilin.context.BaseContext;
import com.bilin.dto.ShoppingCartDTO;
import com.bilin.entity.Dish;
import com.bilin.entity.SetMeal;
import com.bilin.entity.ShoppingCart;
import com.bilin.mapper.DishMapper;
import com.bilin.mapper.SetMealMapper;
import com.bilin.mapper.ShoppingCartMapper;
import com.bilin.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartSerivceImpl implements ShoppingCartService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    @Override
    public void addToCart(ShoppingCartDTO dto){
        // Create ShoppingCart instance and copy properties of the dto to it
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(dto, shoppingCart);

        // Determine whether the item is already in the shopping cart
        // by determining if the dishId, dishFlavor, and userId are ALL the same
        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart cart = shoppingCartMapper.selectBy(shoppingCart);
        if (cart == null){ // the shopping cart currently does not contain the item
            // Fill in missing parameters
            // Determine whether we are adding a dish or a set meal
            if (dto.getDishId() != null){ // we are adding a dish
                Dish dish = dishMapper.selectById(dto.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
            }
            else { // we are adding a set meal
                SetMeal setmeal = setMealMapper.getById(dto.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            // Save item to shopping_cart
            shoppingCartMapper.insert(shoppingCart);
        }
        else { // the shopping cart contains the item
            // increment the amount of the item by 1
            cart.setNumber(cart.getNumber()+1);
            shoppingCartMapper.updateNumber(cart);
        }
    }


    @Override
    public List<ShoppingCart> list() {
        // only return shopping cart for current user
        return shoppingCartMapper.list(BaseContext.getCurrentId());
    }

    @Override
    public void clean() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }

    @Override
    public void removeItem(ShoppingCartDTO dto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(dto, shoppingCart);

        // Determine whether there is one or more than one of that item in the cart
        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart cart = shoppingCartMapper.selectBy(shoppingCart);
        if (cart.getNumber() == 1) { // There is only one of that item in the cart. We need to delete it from the shopping_cart table
            shoppingCartMapper.deleteById(cart.getId());
        }
        else {
            cart.setNumber(cart.getNumber() - 1);
            shoppingCartMapper.updateNumber(cart);
        }

    }


}
