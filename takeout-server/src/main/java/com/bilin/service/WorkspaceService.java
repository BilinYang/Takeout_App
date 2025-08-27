package com.bilin.service;

import com.bilin.vo.BusinessDataVO;
import com.bilin.vo.DishOverViewVO;
import com.bilin.vo.OrderOverViewVO;
import com.bilin.vo.SetMealOverViewVO;
import java.time.LocalDateTime;

public interface WorkspaceService {

    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);

    OrderOverViewVO getOrderOverView();

    DishOverViewVO getDishOverView();

    SetMealOverViewVO getSetMealOverView();

}
