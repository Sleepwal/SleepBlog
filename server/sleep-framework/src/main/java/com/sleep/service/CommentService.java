package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author SleepWalker
 * @since 2023-02-09 21:31:29
 */
public interface CommentService extends IService<Comment> {


    Result<?> commentList(String type, Long articleId, Integer pageNum, Integer pageSize);

    Result<?> addComment(Comment comment);

    Result<?> getCommentListByPage(String commentType, int pageNum, int size);
}

