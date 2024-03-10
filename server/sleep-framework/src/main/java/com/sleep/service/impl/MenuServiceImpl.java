package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.entity.Menu;
import com.sleep.mapper.MenuMapper;
import com.sleep.service.MenuService;
import com.sleep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sleep.constants.MyConstants.*;

/**
 * 菜单权限表(SysMenu)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-02-22 20:59:41
 */
@Service("sysMenuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> getPermissions(Long userId) {
        //用户id为1，为超级管理员，返回所有权限
        if(userId == 1L) {
            //查询
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Menu::getStatus, MENU_NORMAL_STATUS);
            queryWrapper.in(Menu::getMenuType, MENU_TYPE_MENU, MENU_TYPE_BUTTON);
            List<Menu> menus = list(queryWrapper);

            //转换成权限信息列表
            return menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
        }
        //不是超级管理员，返回用户拥有的权限
        return menuMapper.getPermissionsByUserId(userId);
    }

    @Override
    public List<Menu> getRoutersByUserId(Long userId) {
        List<Menu> menus;
        //判断是否管理员
        if(SecurityUtils.isAdmin()) {
            //查询所有目录、菜单Menu
            menus = menuMapper.selectAllRouterMenus();
        } else { //查询当前用户拥有的菜单
            menus = menuMapper.selectRouterMenusByUserId(userId);
        }

        //找出父Menu，设置children
        List<Menu> parentMenuWithChildren = parentMenuSetChildren(menus, 0L);
        return parentMenuWithChildren;
    }

    /**
     * 设置children
     * @author SleepWalker
     */
    private List<Menu> parentMenuSetChildren(List<Menu> menus, long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(doGetChildren(menu.getId(), menus)))
                .collect(Collectors.toList());
    }

    /**
     * 查找该menuId的 子Menu
     * @author SleepWalker
     */
    private List<Menu> doGetChildren(Long menuId, List<Menu> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menuId))
                .collect(Collectors.toList());
    }
}

