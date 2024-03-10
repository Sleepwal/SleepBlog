package com.sleep.mapper;

import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

/**
 * 标签(Tag)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-07-14 21:07:30
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

