package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.UserLoginDto;
import com.sleep.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
