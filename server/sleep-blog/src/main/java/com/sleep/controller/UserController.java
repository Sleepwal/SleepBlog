package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.UserRegisterDto;
import com.sleep.domain.dto.front.UpdateUserDto;
import com.sleep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/12 21:43
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public Result<?> userInfo() {
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public Result<?> updateUserInfo(@Validated @RequestBody UpdateUserDto dto) {
        return userService.updateUserInfo(dto);
    }

    @PostMapping("/register")
    public Result<?> register(@Validated @RequestBody UserRegisterDto user) {
        return userService.register(user);
    }

    @GetMapping("/authorList")
    public Result<?> authorList() {
        return userService.authorList();
    }
}
