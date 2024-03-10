package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(SysMenu)表服务接口
 *
 * @author manson
 * @date 2023/05/14
 */
public interface MenuService extends IService<Menu> {

    List<String> getPermissions(Long userId);

    List<Menu> getRoutersByUserId(Long userId);
}

