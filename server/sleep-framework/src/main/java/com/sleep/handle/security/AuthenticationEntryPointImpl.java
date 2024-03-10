package com.sleep.handle.security;

import com.alibaba.fastjson.JSON;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 认证入口点impl
 *
 * @author manson
 * @date 2023/05/14
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();

        Result<?> result = null;
        //登录时认证失败
        if(e instanceof BadCredentialsException) {
            result = Result.fail(ResultEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else if(e instanceof InsufficientAuthenticationException) {   //需要登录
            result = Result.fail(ResultEnum.NEED_LOGIN);
        } else {    //其他错误
            result = Result.fail(ResultEnum.SYSTEM_ERROR.getCode(), e.getMessage());
        }

        //响应发送到前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
    }
}
