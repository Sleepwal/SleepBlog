package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.UpdatePasswordDto;
import com.sleep.domain.dto.UpdateUserInfoDto;
import com.sleep.domain.dto.admin.AddUserDto;
import com.sleep.domain.entity.User;
import com.sleep.service.UserService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/11 21:01
 */
@Validated
@RestController
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result<?> userInfo() {
        return userService.userInfo();
    }

    @PostMapping("/addUser")
    public Result<?> addUser(@Validated @RequestBody AddUserDto dto) {
        return userService.addUser(dto);
    }

    @PutMapping("/updateUserInfo")
    public Result<?> updateUserInfo(@Validated @RequestBody UpdateUserInfoDto dto) {
        User user = BeanCopyUtils.copyBean(dto, User.class);
        return userService.updateUserInfo(user);
    }

    @PutMapping("/updatePassword")
    public Result<?> updatePassword(@Validated @RequestBody UpdatePasswordDto dto) {
        User user = BeanCopyUtils.copyBean(dto, User.class);
        return userService.updateUserInfo(user);
    }

    @GetMapping("/userList")
    public Result<?> userList() {
        return Result.success(userService.list());
    }

    @DeleteMapping("/deleteUser/{id}")
    public Result<?> deleteUser(@NotNull(message = "用户id不能为空")
                                    @PathVariable("id")
                                    Long id) {
        boolean remove = userService.removeById(id);
        if(!remove)
            return Result.fail(500, "删除用户失败");
        return Result.success();
    }

    @GetMapping("/userTotalCount")
    public Result<?> userTotalCount() {
        return Result.success(userService.count());
    }
}
