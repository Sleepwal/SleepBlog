package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-02-22 21:34:44
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRolesByUserId(Long userId);
}

