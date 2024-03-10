package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单权限表(SysMenu)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-02-22 20:59:39
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> getPermissionsByUserId(Long id);

    List<Menu> selectAllRouterMenus();

    List<Menu> selectRouterMenusByUserId(Long userId);
}

