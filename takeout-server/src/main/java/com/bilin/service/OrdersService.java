package com.bilin.service;

import com.bilin.dto.*;
import com.bilin.entity.Orders;
import com.bilin.result.PageResult;
import com.bilin.vo.OrderPaymentVO;
import com.bilin.vo.OrderStatisticsVO;
import com.bilin.vo.OrderSubmitVO;
import com.bilin.vo.OrderVO;
import org.springframework.stereotype.Service;

@Service
public interface OrdersService {

    OrderSubmitVO submit(OrdersSubmitDTO dto);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    void cancelOrder(OrdersCancelDTO dto) throws Exception;

    OrderStatisticsVO getStats();

    void completeOrder(Long id);

    void rejectOrder(OrdersRejectionDTO dto) throws Exception;

    void confirmOrder(OrdersConfirmDTO dto);

    OrderVO getOrderDetails(Long id);

    void deliverOrder(Long id);

    PageResult conditionSearch(OrdersPageQueryDTO dto);

    void sendExpediteOrderRequest(Long id);

    void makeAnotherOrder(Long orderId);

    PageResult userGetOrderHistory(int page, int pageSize, int status);

    void userCancelOrder(Long id) throws Exception;
}
