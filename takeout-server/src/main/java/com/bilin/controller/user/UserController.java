package com.bilin.controller.user;

import com.bilin.constant.JwtClaimsConstant;
import com.bilin.dto.UserLoginDTO;
import com.bilin.entity.User;
import com.bilin.properties.JwtProperties;
import com.bilin.result.Result;
import com.bilin.service.UserService;
import com.bilin.utils.JwtUtil;
import com.bilin.vo.EmployeeLoginVO;
import com.bilin.vo.UserLoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags="User API")
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO dto){
        log.info("User Login: {}", dto);
        User user = userService.login(dto);

        // generate JWT token
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
            jwtProperties.getUserSecretKey(),
            jwtProperties.getUserTtl(),
            claims
        );

        // construct UserVO object if login is successful
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(user.getOpenid())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

}
