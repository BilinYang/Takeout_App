package com.bilin.controller.admin;

import com.bilin.result.Result;
import com.bilin.service.ReportService;
import com.bilin.vo.OrderReportVO;
import com.bilin.vo.SalesTop10ReportVO;
import com.bilin.vo.TurnoverReportVO;
import com.bilin.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@Api(tags="Statistics API")
@RequestMapping("/admin/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation("Generate Turnover Report")
    @GetMapping("/turnoverStatistics")
    public Result<TurnoverReportVO> turnoverStatistics(@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                     @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end) {
        log.info("Generate Turnover Report from {} to {}", begin, end);
        TurnoverReportVO vo = reportService.turnoverStatistics(begin, end);
        return Result.success(vo);
    }

    @ApiOperation("Generate User Statistics Report")
    @GetMapping("/userStatistics")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end) {
        log.info("Generate User Statistics Report from {} to {}", begin, end);
        UserReportVO vo = reportService.userStatistics(begin, end);
        return Result.success(vo);
    }

    @ApiOperation("Generate Order Statistics Report")
    @GetMapping("/ordersStatistics")
    public Result<OrderReportVO> orderStatistics(@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                                 @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end) {
        log.info("Generate Order Statistics Report from {} to {}", begin, end);
        OrderReportVO vo = reportService.orderStatistics(begin, end);
        return Result.success(vo);
    }

    // Get the top 10 most sold dishes (need to be from valid orders)
    @ApiOperation("Generate Top 10 Dishes Statistics Report")
    @GetMapping("/top10")
    public Result<SalesTop10ReportVO> top10Statistics(@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate begin,
                                                      @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate end) {
        log.info("Generate Top 10 Dishes Report from {} to {}", begin, end);
        SalesTop10ReportVO vo = reportService.top10Statistics(begin, end);
        log.info("VO: {}", vo.toString());
        return Result.success(vo);
    }

}
