package com.sleep.mapper;

import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.KnowCate;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库 文章类型(KnowCate)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-05-30 22:09:02
 */
@Mapper
public interface KnowCateMapper extends BaseMapper<KnowCate> {

}

