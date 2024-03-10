package com.sleep.mapper;

import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.KnowArticle;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库的文章(KnowArticle)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-05-30 22:08:35
 */
@Mapper
public interface KnowArticleMapper extends BaseMapper<KnowArticle> {

}

