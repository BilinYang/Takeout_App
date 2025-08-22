package com.bilin.service;

import com.bilin.dto.UserLoginDTO;
import com.bilin.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User login(UserLoginDTO dto);
}
