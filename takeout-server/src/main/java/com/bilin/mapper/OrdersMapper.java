package com.bilin.mapper;

import com.bilin.dto.OrdersCancelDTO;
import com.bilin.dto.OrdersConfirmDTO;
import com.bilin.dto.OrdersPageQueryDTO;
import com.bilin.dto.OrdersRejectionDTO;
import com.bilin.entity.Orders;
import com.bilin.vo.OrderStatisticsVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OrdersMapper {

    void insert(Orders orders);

    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    void update(Orders orders);

    @Select("select count(*) from orders where status=#{status}")
    Integer countByStatus(Integer status);

    Page<Orders> pageQuery(OrdersPageQueryDTO dto);
}
