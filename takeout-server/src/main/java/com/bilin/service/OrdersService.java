package com.bilin.service;

import com.bilin.dto.OrdersPaymentDTO;
import com.bilin.dto.OrdersSubmitDTO;
import com.bilin.vo.OrderPaymentVO;
import com.bilin.vo.OrderSubmitVO;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {

    OrderSubmitVO submit(OrdersSubmitDTO dto);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

}
