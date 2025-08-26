package com.bilin.mapper;

import com.bilin.dto.ShoppingCartDTO;
import com.bilin.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ShoppingCartMapper {

    ShoppingCart selectBy(ShoppingCart shoppingCart);

    @Insert("insert INTO shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time)" +
            "VALUES (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number=#{number} where id=#{id}")
    void updateNumber(ShoppingCart cart);

    @Select("select * from shopping_cart where user_id=#{userId}")
    List<ShoppingCart> list(Long currentId);

    // clear cart
    @Delete("delete from shopping_cart where user_id=#{userId}")
    void deleteByUserId(Long currentId);

    @Delete("delete from shopping_cart where id=#{id}")
    void deleteById(Long id);

    void insertBatch(List<ShoppingCart> shoppingCartList);
}
