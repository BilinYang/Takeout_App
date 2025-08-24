package com.bilin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bilin.constant.MessageConstant;
import com.bilin.dto.UserLoginDTO;
import com.bilin.entity.User;
import com.bilin.exception.LoginFailedException;
import com.bilin.mapper.UserMapper;
import com.bilin.properties.WeChatProperties;
import com.bilin.service.UserService;
import com.bilin.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO dto) {
        // Construct user authentication request through HttpClient
        // construct request parameters
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", weChatProperties.getAppid());
        paramMap.put("secret", weChatProperties.getSecret());
        paramMap.put("js_code", dto.getCode());
        paramMap.put("grant_type", "authorization_code");
        String res = HttpClientUtil.doGet(WX_LOGIN, paramMap);

        // Parse response data and get openid (the unique id that WeChat gives to all users)
        JSONObject jsonObject = JSON.parseObject(res);
        String openid = jsonObject.get("openid").toString();;
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.USER_NOT_LOGIN);
        }

        // Determine if the user is a new user (by searching the user data table using the openid)
        User user =  userMapper.selectByOpenid(openid);

        // If the user is a new user, initialize the user in the user data table
        if (user == null) { // this is a new user
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(LocalDateTime.now());
            user.setName(openid.substring(0, 5));
            userMapper.insertUser(user);
        }

        // Otherwise, directly return the user object data
        return user;

    }
}
