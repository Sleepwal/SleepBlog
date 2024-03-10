package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-01-31 22:14:00
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

