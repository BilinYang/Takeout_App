package com.bilin.mapper;

import com.bilin.dto.*;
import com.bilin.entity.Orders;
import com.bilin.vo.OrderStatisticsVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Select("select * from orders where status=#{pendingPayment} and order_time < #{thresholdTime}")
    List<Orders> selectByStatusAndOrderTime(Integer pendingPayment, LocalDateTime thresholdTime);

    @Select("select sum(amount) from orders where status=#{status} and order_time between #{beginTime} and #{endTime}")
    Double sumByMap(Map map);

    Integer countByMap(Map map);

    @MapKey("name")
    List<GoodsSalesDTO> sumTop10(Map map);
}
