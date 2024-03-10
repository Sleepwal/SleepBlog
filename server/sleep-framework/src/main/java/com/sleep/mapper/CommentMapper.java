package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论表(Comment)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-02-09 21:31:29
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}

