package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.UserLoginDto;
import com.sleep.domain.entity.User;
import com.sleep.exception.SystemException;
import com.sleep.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/1/31 22:12
 */

@Validated
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result<?> login(@Validated @RequestBody UserLoginDto user) {
        return loginService.login(user);
    }

    @PostMapping("/logout")
    public Result<?> logout() {
        return loginService.logout();
    }

}
