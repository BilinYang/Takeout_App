package com.bilin.mapper;

import com.bilin.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    void insertBatch(List<OrderDetail> orderDetailList);

    @Select("select * from order_detail where order_id=#{orderId}")
    List<OrderDetail> getOrderDetailList(Long orderId);
}
