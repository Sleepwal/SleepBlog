package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.UserRegisterDto;
import com.sleep.domain.dto.admin.AddUserDto;
import com.sleep.domain.dto.front.UpdateUserDto;
import com.sleep.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author manson
 * @date 2023/05/14
 */
public interface UserService extends IService<User> {

    Result<?> userInfo();

    Result<?> updateUserInfo(User user);
    Result<?> updateUserInfo(UpdateUserDto user);

    Result<?> register(UserRegisterDto user);

    Result<?> authorList();

    Result<?> addUser(AddUserDto dto);
}

