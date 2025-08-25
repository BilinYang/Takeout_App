package com.bilin.mapper;

import com.bilin.entity.Orders;
import org.apache.ibatis.annotations.Select;

public interface OrdersMapper {

    void insert(Orders orders);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    void update(Orders orders);

}
