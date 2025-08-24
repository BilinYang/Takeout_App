package com.bilin.interceptor;

import com.bilin.constant.JwtClaimsConstant;
import com.bilin.context.BaseContext;
import com.bilin.properties.JwtProperties;
import com.bilin.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Determine whether the intercepted method is a Controller
        if (!(handler instanceof HandlerMethod)) {
            // if the method is not a dynamic method, let pass
            return true;
        }

        String token = request.getHeader(jwtProperties.getUserTokenName());

        // parse and verify token
        try {
            log.info("jwt verification:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());

            // save the id to ThreadLocal for later use
            BaseContext.setCurrentId(userId);
            log.info("Current user id：{}", userId);
            BaseContext.setCurrentId(userId);
            // let pass
            return true;
        } catch (Exception ex) {
            // not let pass and return 401 status
            response.setStatus(401);
            return false;
        }
    }
}
