package com.bilin.controller.admin;

import com.bilin.result.Result;
import com.bilin.service.WorkspaceService;
import com.bilin.vo.BusinessDataVO;
import com.bilin.vo.DishOverViewVO;
import com.bilin.vo.OrderOverViewVO;
import com.bilin.vo.SetMealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api(tags = "Workspace API")
public class WorkSpaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/businessData")
    @ApiOperation("Current Day Data Query API")
    public Result<BusinessDataVO> businessData(){
        // Get the time of the start of the current day
        LocalDateTime begin = LocalDateTime.now().with(LocalTime.MIN);
        // Get the time of the end of the current day
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        BusinessDataVO businessDataVO = workspaceService.getBusinessData(begin, end);
        return Result.success(businessDataVO);
    }

    @GetMapping("/overviewOrders")
    @ApiOperation("Order Overview API")
    public Result<OrderOverViewVO> orderOverView(){ return Result.success(workspaceService.getOrderOverView());}


    @GetMapping("/overviewDishes")
    @ApiOperation("Dishes Overview API")
    public Result<DishOverViewVO> dishOverView(){
        return Result.success(workspaceService.getDishOverView());
    }

    @GetMapping("/overviewSetmeals")
    @ApiOperation("Set Meal Overview API")
    public Result<SetMealOverViewVO> setmealOverView(){
        return Result.success(workspaceService.getSetMealOverView());
    }
}
