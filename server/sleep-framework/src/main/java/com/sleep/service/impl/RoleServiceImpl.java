package com.sleep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.entity.Role;
import com.sleep.mapper.RoleMapper;
import com.sleep.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-02-22 21:34:46
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoles(Long userId) {
        if(userId == 1L) {
            ArrayList<String> roles = new ArrayList<>();
            roles.add("admin");
            return roles;
        }

        return roleMapper.getRolesByUserId(userId);
    }
}

