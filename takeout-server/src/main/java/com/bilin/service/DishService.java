package com.bilin.service;

import com.bilin.dto.DishDTO;
import com.bilin.dto.DishPageQueryDTO;
import com.bilin.result.PageResult;
import com.bilin.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {

    void addDish(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dto);

    void delete(List<Long> ids);

    void startOrStop(Integer status, Long id);

    DishVO getById(Long id);
}
