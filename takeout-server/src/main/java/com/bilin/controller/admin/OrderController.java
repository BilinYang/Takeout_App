package com.bilin.controller.admin;


import com.bilin.dto.OrdersCancelDTO;
import com.bilin.dto.OrdersConfirmDTO;
import com.bilin.dto.OrdersPageQueryDTO;
import com.bilin.dto.OrdersRejectionDTO;
import com.bilin.entity.Orders;
import com.bilin.result.PageResult;
import com.bilin.result.Result;
import com.bilin.service.OrdersService;
import com.bilin.vo.OrderStatisticsVO;
import com.bilin.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "Order API (Admin)")
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @ApiOperation("Cancel Order")
    @PutMapping("/cancel")
    public Result cancelOrder(@RequestBody OrdersCancelDTO dto) throws Exception {
        log.info("Cancel Order: {}",dto);
        ordersService.cancelOrder(dto);
        return Result.success();
    }

    @ApiOperation("Get Statistics")
    @GetMapping("/statistics")
    public Result getStats() {
        log.info("Get Order Statistics");
        OrderStatisticsVO vo = ordersService.getStats();
        return Result.success(vo);
    }

    @ApiOperation("Complete Order")
    @PutMapping("/complete/{id}")
    public Result completeOrder(@PathVariable("id") Long id) {
        log.info("Complete Order: {}",id);
        ordersService.completeOrder(id);
        return Result.success();
    }

    @ApiOperation("Reject Order")
    @PutMapping("/rejection")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO dto) throws Exception {
        log.info("Reject Order: {}",dto);
        ordersService.rejectOrder(dto);
        return Result.success();
    }

    @ApiOperation("Confirm Order")
    @PutMapping("/confirm")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO dto) {
        log.info("Confirm Order: {}", dto);
        ordersService.confirmOrder(dto);
        return Result.success();
    }

    @ApiOperation("Get Order Details")
    @GetMapping("/details/{id}")
    public Result getOrderDetails(@PathVariable("id") Long id) {
        log.info("Get Order Details: {}",id);
        OrderVO order = ordersService.getOrderDetails(id);
        return Result.success(order);
    }

    @ApiOperation("Deliver Order")
    @PutMapping("/delivery/{id}")
    public Result deliverOrder(@PathVariable("id") Long id) {
        log.info("Deliver Order: {}",id);
        ordersService.deliverOrder(id);
        return Result.success();
    }

    @ApiOperation("Search Orders by Conditions")
    @GetMapping("/conditionSearch")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO dto){
        log.info("Search Orders by Conditions: {}", dto);
        PageResult pageResult = ordersService.conditionSearch(dto);
        return Result.success(pageResult);
    }


}
