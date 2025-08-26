package com.bilin.controller.user;

import com.bilin.dto.OrdersPaymentDTO;
import com.bilin.dto.OrdersSubmitDTO;
import com.bilin.entity.Orders;
import com.bilin.result.PageResult;
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
@Api(tags="Orders API (User)")
@RequestMapping("/user/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @ApiOperation("Submit Order")
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

    @ApiOperation("Expedite Order")
    @GetMapping("/reminder/{id}")
    public Result sendExpediteOrderRequest(@PathVariable("id") Long id){
        log.info("Send Expedite Order Request to Order with ID: {}", id);
        ordersService.sendExpediteOrderRequest(id);
        return Result.success();
    }

    @ApiOperation("Make Another Order")
    @PostMapping("/repetition/{id}")
    public Result makeAnotherOrder(@PathVariable("id") Long id){
        log.info("Make Another Order with for Order with ID: {}", id);
        ordersService.makeAnotherOrder(id);
        return Result.success();
    }

    @ApiOperation("Get Order History")
    @GetMapping("/historyOrders")
    public Result<PageResult> getOrderHistory(int page, int pageSize, int status){
        log.info("Get Order History");
        PageResult pageResult = ordersService.userGetOrderHistory(page, pageSize, status);
        return Result.success(pageResult);
    }

    @ApiOperation("User Cancel Order")
    @PutMapping("/cancel/{id}")
    public Result userCancelOrder(@PathVariable("id") Long id) throws Exception {
        log.info("Cancel Order with ID: {}", id);
        ordersService.userCancelOrder(id);
        return Result.success();
    }

}
