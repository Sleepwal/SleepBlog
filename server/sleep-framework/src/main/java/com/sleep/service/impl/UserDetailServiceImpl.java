package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleep.domain.entity.LoginUser;
import com.sleep.domain.entity.User;
import com.sleep.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/1/31 22:24
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);

        //判断是否有用户
        if (Objects.isNull(user)) {
            throw new RuntimeException("该用户不存在");    //无则抛出异常
        }

        //TODO 查询权限信息

        //返回用户信息
        return new LoginUser(user);
    }
}
