package com.bilin.service.impl;

import com.bilin.dto.UserLoginDTO;
import com.bilin.entity.User;
import com.bilin.mapper.UserMapper;
import com.bilin.service.UserService;
import com.bilin.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO dto) {
        // Construct user authentication request through HttpClient

        // Parse response data and get openid (the unique id that WeChat gives to all users)

        // Determine if the user is a new user (by searching the user data table using the openid)

        // If the user is a new user, initialize the user in the user data table

        // Otherwise, directly return the user object data
        return null;

    }
}
