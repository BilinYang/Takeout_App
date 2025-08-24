package com.bilin.service;

import com.bilin.dto.ShoppingCartDTO;
import com.bilin.entity.ShoppingCart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingCartService {

    void addToCart(ShoppingCartDTO dto);

    List<ShoppingCart> list();

    void clean();

    void removeItem(ShoppingCartDTO dto);
}
