package com.sleep.service;

import com.sleep.domain.Result;
import com.sleep.domain.dto.UserLoginDto;
import com.sleep.domain.entity.User;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/1/31 22:21
 */
public interface AdminLoginService {
    Result<?> login(UserLoginDto user);

    Result<?> logout();
}
