package com.bilin.controller.user;

import com.bilin.dto.UserLoginDTO;
import com.bilin.entity.User;
import com.bilin.result.Result;
import com.bilin.service.UserService;
import com.bilin.vo.UserLoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="User API")
@Slf4j
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO dto){
        log.info("User Login: {}", dto);
        User user = userService.login(dto);

        // generate JWT token

        // construct UserVO object if login is successful


        return Result.success();
    }
}
