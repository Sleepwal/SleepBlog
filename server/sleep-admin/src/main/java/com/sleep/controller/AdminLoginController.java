package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.UserLoginDto;
import com.sleep.domain.entity.LoginUser;
import com.sleep.domain.entity.Menu;
import com.sleep.domain.entity.User;
import com.sleep.domain.vo.AdminUserInfoVo;
import com.sleep.domain.vo.RoutersVo;
import com.sleep.domain.vo.UserInfoVo;
import com.sleep.service.AdminLoginService;
import com.sleep.service.MenuService;
import com.sleep.service.RoleService;
import com.sleep.utils.BeanCopyUtils;
import com.sleep.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/1/31 22:12
 */

@Validated
@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @ApiOperation("管理员用户登录")
    @PostMapping("/user/login")
    public Result<?> login(@Validated @RequestBody UserLoginDto user) {
        return adminLoginService.login(user);
    }

    @ApiOperation("管理员用户注销")
    @PostMapping("/user/logout")
    public Result<?> logout() {
        return adminLoginService.logout();
    }

    @ApiOperation("管获取用户信息")
    @GetMapping("/getInfo")
    public Result<?> getInfo() {
        //获取用户id
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUser().getId();

        //1.查询权限信息
        List<String> permissions = menuService.getPermissions(userId);

        //2.查询角色信息
        List<String> roles = roleService.getRoles(userId);

        //3.获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(permissions, roles, userInfoVo);

        return Result.success(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public Result<?> getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.getRoutersByUserId(userId);

        return Result.success(new RoutersVo(menus));
    }
}
