package com.bilin.controller.user;

import com.bilin.dto.OrdersPaymentDTO;
import com.bilin.dto.OrdersSubmitDTO;
import com.bilin.entity.Orders;
import com.bilin.result.Result;
import com.bilin.service.OrdersService;
import com.bilin.vo.OrderPaymentVO;
import com.bilin.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags="Orders API")
@RequestMapping("/user/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO dto){
        log.info("Submit Order");
        OrderSubmitVO vo = ordersService.submit(dto);
        return Result.success(vo);
    }

    @PutMapping("/payment")
    @ApiOperation("Submit Payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("Make payment：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = ordersService.payment(ordersPaymentDTO);
        log.info("Generate prepay transaction order：{}", orderPaymentVO);

        // Payment simulation succeeded, change order status in the orders table
        ordersService.paySuccess(ordersPaymentDTO.getOrderNumber());
        log.info("Simulated transaction successfully completed：{}",ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }


}
