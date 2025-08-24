package com.bilin.vo;

import com.bilin.entity.OrderDetail;
import com.bilin.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders implements Serializable {

    // Order dish information
    private String orderDishes;

    // Order details
    private List<OrderDetail> orderDetailList;

}
