package com.sleep.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.ArticleTagRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章和标签关系表(ArticleTagRelation)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-07-14 21:54:30
 */
@Mapper
public interface ArticleTagRelationMapper extends BaseMapper<ArticleTagRelation> {

}

