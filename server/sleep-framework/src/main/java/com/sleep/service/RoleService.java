package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author SleepWalker
 * @since 2023-02-22 21:34:46
 */
public interface RoleService extends IService<Role> {

    List<String> getRoles(Long userId);
}

