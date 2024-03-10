package com.sleep.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Constants;
import com.sleep.constants.MyConstants;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.UserRegisterDto;
import com.sleep.domain.dto.admin.AddUserDto;
import com.sleep.domain.dto.front.UpdateUserDto;
import com.sleep.domain.entity.Article;
import com.sleep.domain.entity.User;
import com.sleep.domain.vo.UserInfoVo;
import com.sleep.domain.vo.front.AuthorVo;
import com.sleep.exception.SystemException;
import com.sleep.mapper.ArticleMapper;
import com.sleep.mapper.UserMapper;
import com.sleep.service.UserService;
import com.sleep.utils.BeanCopyUtils;
import com.sleep.utils.RedisCache;
import com.sleep.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-02-12 21:44:55
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisCache redisCache;


    @Override
    public Result<?> userInfo() {
        //获取userID
        Long userId = SecurityUtils.getUserId();
        //从数据库查询用户信息
        User user = getById(userId);

        //封装vo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //返回
        return Result.success(userInfoVo);
    }

    @Override
    public Result<?> updateUserInfo(User user) {
        if (Objects.nonNull(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        updateById(user);
        return Result.success();
    }

    @Override
    public Result<?> updateUserInfo(UpdateUserDto dto) {
        User user = BeanCopyUtils.copyBean(dto, User.class);

        if (Objects.nonNull(dto.getOldPassword())) { //有密码
            //验证旧密码
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getUsername, dto.getUsername());
            User one = getOne(wrapper);

            if (Objects.nonNull(one)) { //有该用户名，验证密码
                if (passwordEncoder.matches(dto.getOldPassword(), one.getPassword()))
                    //设置新密码
                    user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            }
        }

        updateById(user);
        return Result.success(Objects.isNull(user.getPassword()) ?
                "修改用户信息成功，密码无修改！" :
                "修改用户信息成功，密码已修改！");
    }

    @Override
    public Result<?> addUser(AddUserDto dto) {
        //用户名查重
        if (userNameExist(dto.getUsername())) {
            throw new SystemException(ResultEnum.USERNAME_EXIST);
        }
        //邮箱查重
        if (emailExist(dto.getEmail())) {
            throw new SystemException(ResultEnum.EMAIL_EXIST);
        }

        //密码加密
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        //保存到数据库
        User user = BeanCopyUtils.copyBean(dto, User.class);
        boolean save = save(user);
        if (!save)
            return Result.fail(ResultEnum.SAVE_DATABASE_ERROR);

        return Result.success();
    }

    @Override
    public Result<?> register(UserRegisterDto userRegisterDto) {
        //验证码校验
        String cacheCode = redisCache.getCacheObject(Constants.KAPTCHA_SESSION_KEY);
        if (!cacheCode.equals(userRegisterDto.getCode()))
            throw new RuntimeException("验证码错误或已过期");

        //用户名查重
        if (userNameExist(userRegisterDto.getUsername())) {
            throw new SystemException(ResultEnum.USERNAME_EXIST);
        }
        //邮箱查重
        if (emailExist(userRegisterDto.getEmail())) {
            throw new SystemException(ResultEnum.EMAIL_EXIST);
        }

        //密码加密
        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        //保存到数据库
        User user = BeanCopyUtils.copyBean(userRegisterDto, User.class);
        boolean save = save(user);
        if (!save)
            return Result.fail(ResultEnum.SAVE_DATABASE_ERROR);

        return Result.success();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, userName);
        return count(queryWrapper) > 0;
    }

    private boolean emailExist(String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        return count(queryWrapper) > 0;
    }

    @Override
    public Result<?> authorList() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //只要管理员
        queryWrapper.eq(User::getType, MyConstants.SUPER_USER_TYPE);
        queryWrapper.eq(User::getStatus, MyConstants.USER_NORMAL_STATUS);
        List<User> list = list(queryWrapper);

        List<AuthorVo> authorVos = BeanCopyUtils.copyBeanList(list, AuthorVo.class);
        //设置文章数
        for (int i = 0; i < list.size(); i++) {
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Article::getCreateBy, list.get(i).getId());
            authorVos.get(i).setCount(articleMapper.selectCount(wrapper));
        }

        return Result.success(authorVos);
    }


}

