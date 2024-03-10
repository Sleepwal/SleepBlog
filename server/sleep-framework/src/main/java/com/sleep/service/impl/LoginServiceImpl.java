package com.sleep.service.impl;

import com.google.code.kaptcha.Constants;
import com.sleep.domain.Result;
import com.sleep.domain.dto.UserLoginDto;
import com.sleep.domain.entity.LoginUser;
import com.sleep.domain.vo.UserInfoVo;
import com.sleep.domain.vo.UserLoginVo;
import com.sleep.service.LoginService;
import com.sleep.utils.BeanCopyUtils;
import com.sleep.utils.JwtUtil;
import com.sleep.utils.RedisCache;
import com.sleep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.sleep.constants.CommonConstants.LOGIN_USER_TIME_TO_LIVE;
import static com.sleep.constants.MyConstants.LOGIN_USER_KEY;


/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/1/31 22:22
 */

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public Result<?> login(UserLoginDto user) {
        //校验验证码
        String cacheCode = redisCache.getCacheObject(Constants.KAPTCHA_SESSION_KEY);
        if (!Objects.equals(cacheCode, user.getCode())) {
            throw new RuntimeException("验证码错误或已过期");
        }

        //1.用AuthenticationManager的实现类ProviderManager中的方法进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误！");
        }

        //2.生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //3.存入redis
        redisCache.setCacheObject(LOGIN_USER_KEY + userId, loginUser, LOGIN_USER_TIME_TO_LIVE, TimeUnit.DAYS);

        //4.封装返回结果
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo userLoginVo = new UserLoginVo(jwt, userInfoVo);
        return Result.success(userLoginVo);
    }

    @Override
    public Result<?> logout() {
        //根据user的id，删除redis中的用户信息
        redisCache.deleteObject(LOGIN_USER_KEY + SecurityUtils.getUserId());
        return Result.success();
    }
}
