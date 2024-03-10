package com.sleep.filter;

import com.alibaba.fastjson.JSON;
import com.sleep.domain.Result;
import com.sleep.domain.entity.LoginUser;
import com.sleep.utils.JwtUtil;
import com.sleep.utils.RedisCache;
import com.sleep.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.sleep.constants.MyConstants.LOGIN_ADMIN_USER_KEY;
import static com.sleep.domain.ResultEnum.NEED_LOGIN;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/7 17:59
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //1.获取token
        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)) {
            //不需要token的请求，即不需要登录，放行
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        //2.解析token, 得到用户id
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();

            //token超时或非法，需要重新登录
            Result<?> result = Result.fail(NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        //3.以 LOGIN_ADMIN_USER_KEY + 用户id 为key, 在redis中查询用户信息
        LoginUser loginUser = redisCache.getCacheObject(LOGIN_ADMIN_USER_KEY + claims.getSubject());
        if(Objects.isNull(loginUser)) {
            //token超时或非法，需要重新登录
            Result<?> result = Result.fail(NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }

        //4.存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
