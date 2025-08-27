package com.bilin.service;

import com.bilin.vo.OrderReportVO;
import com.bilin.vo.SalesTop10ReportVO;
import com.bilin.vo.TurnoverReportVO;
import com.bilin.vo.UserReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface ReportService {

    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    OrderReportVO orderStatistics(LocalDate begin, LocalDate end);

    SalesTop10ReportVO top10Statistics(LocalDate begin, LocalDate end);
}
