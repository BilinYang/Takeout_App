package com.bilin.service.impl;

import com.bilin.dto.GoodsSalesDTO;
import com.bilin.entity.Orders;
import com.bilin.mapper.OrdersMapper;
import com.bilin.mapper.UserMapper;
import com.bilin.service.ReportService;
import com.bilin.vo.OrderReportVO;
import com.bilin.vo.SalesTop10ReportVO;
import com.bilin.vo.TurnoverReportVO;
import com.bilin.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end){
        // 1. Prepare date data
        List<LocalDate> datesList = getDatesList(begin, end);
        // log.info("Dates = {}", datesList);

        // 2. Prepare turnover data
        // Turnover definiton: the amount of money made per day
        // Search orders data table to get status and order_time
        List turnoverList = new ArrayList<Long>();
        datesList.forEach(date -> {
            Map map = new HashMap();
            map.put("status", Orders.COMPLETED);
            map.put("beginTime", LocalDateTime.of(date, LocalTime.MIN));
            map.put("endTime", LocalDateTime.of(date, LocalTime.MAX));
            Double turnover = ordersMapper.sumByMap(map);
            turnover = turnover == null ? 0 : turnover;
            turnoverList.add(turnover);
        });

        // 3. Construct instance of TurnoverReportVO and return it
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(datesList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        // Construct dataList
        List<LocalDate> datesList = getDatesList(begin, end);

        List<Integer> newUsersList = new ArrayList<>();
        List<Integer> totalUsersList = new ArrayList<>();
        datesList.forEach(date -> {
            // Construct newUserList
            Map map = new HashMap();
            map.put("beginTime", LocalDateTime.of(date, LocalTime.MIN));
            map.put("endTime", LocalDateTime.of(date, LocalTime.MAX));
            Integer numberNewUsers = userMapper.countByMap(map);
            newUsersList.add(numberNewUsers);

            // Construct totalUserList
            map.put("beginTime", null);
            Integer numberTotalUsers = userMapper.countByMap(map);
            totalUsersList.add(numberTotalUsers);
        });

        // Construct instance of UserReportVO and return it
        return UserReportVO.builder()
                .dateList(StringUtils.join(datesList, ","))
                .newUserList(StringUtils.join(newUsersList, ","))
                .totalUserList(StringUtils.join(totalUsersList, ","))
                .build();
    }

    @Override
    public OrderReportVO orderStatistics(LocalDate begin, LocalDate end) {
        // Get datesList
        List<LocalDate> datesList = getDatesList(begin, end);

        List<Integer> validOrderCountList = new ArrayList<>();
        List<Integer> totalOrderCountList = new ArrayList<>();
        // Integer totalOrderCount = 0; // the total number of orders between 'begin' and 'end'
        // Integer validOrderCount = 0; // the number of valid orders between 'begin' and 'end'

        for (LocalDate date : datesList) {
            Map map = new HashMap();
            map.put("beginTime", LocalDateTime.of(date, LocalTime.MIN));
            map.put("endTime", LocalDateTime.of(date, LocalTime.MAX));
            Integer totalOrdersThisDay = ordersMapper.countByMap(map);
            totalOrderCountList.add(totalOrdersThisDay);


            map.put("status", Orders.COMPLETED);
            Integer validOrdersThisDay = ordersMapper.countByMap(map);
            validOrderCountList.add(validOrdersThisDay);

            // Increment totalOrderCount by the number of orders this day (totalOrdersThisDay)
            // totalOrderCount += totalOrdersThisDay;
            // Increment validOrderCount by the number of valid orders this day (validOrdersThisDay)
            // validOrderCount += validOrdersThisDay;
        }

        // (both methods work the same way, and they are equivalent to a for loop that accumulates)
        Integer totalOrderCount = totalOrderCountList.stream().reduce(0, Integer::sum);
        Integer validOrderCount = validOrderCountList.stream().reduce(Integer::sum).get();

        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0) {
            orderCompletionRate = (validOrderCount + 0.0) / totalOrderCount;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(datesList, ","))
                .orderCountList(StringUtils.join(totalOrderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }


    private List<LocalDate> getDatesList(LocalDate begin, LocalDate end) {
        List<LocalDate> datesList = new ArrayList<LocalDate>();
        while (!begin.isAfter(end)){
            datesList.add(begin);
            begin = begin.plusDays(1);
        }
        return datesList;
    }


    @Override
    public SalesTop10ReportVO top10Statistics(LocalDate begin, LocalDate end) {
        // 1. Get nameList (the list with the top 10 most sold dishes)
        // Search orders and order_detail data tables to get the names and dishes/set meals
        // with the most sales from completed orders
        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();

        Map map = new HashMap();
        map.put("status", Orders.COMPLETED);
        map.put("beginTime", LocalDateTime.of(begin, LocalTime.MIN));
        map.put("endTime", LocalDateTime.of(end, LocalTime.MAX));
        List<GoodsSalesDTO> list = ordersMapper.sumTop10(map);

        // 2. Get numberList (the list with the number of sales for each of the top 10 dishes)
        for ( GoodsSalesDTO dto: list){
            nameList.add(dto.getName());
            numberList.add(dto.getNumber());
        }

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

}
