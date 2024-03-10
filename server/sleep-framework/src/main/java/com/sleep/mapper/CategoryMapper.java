package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author SleepWalker
 * @since 2022-12-15 12:57:36
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}

