package com.bilin.service;

import com.bilin.dto.DishDTO;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.result.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public interface DishService {

    void addDish(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dto);
}
