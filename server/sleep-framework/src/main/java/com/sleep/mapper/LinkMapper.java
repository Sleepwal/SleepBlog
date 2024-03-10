package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author SleepWalker
 * @since 2022-12-19 20:50:03
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

