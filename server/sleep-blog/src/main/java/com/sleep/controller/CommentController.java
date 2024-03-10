package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.entity.Comment;
import com.sleep.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.sleep.constants.MyConstants.ARTICLE_COMMENT;
import static com.sleep.constants.MyConstants.LINK_COMMENT;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/9 21:32
 */

@Validated
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public Result<?> commentList(@NotNull(message = "文章ID不能为空") Long articleId,
                                 @NotNull(message = "分页起始页数不能能为空") Integer pageNum,
                                 @NotNull(message = "分页大小不能为空") Integer pageSize) {
        return commentService.commentList(ARTICLE_COMMENT, articleId, pageNum, pageSize);
    }

    @PostMapping("/addComment")
    public Result<?> addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public Result<?> linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(LINK_COMMENT, null, pageNum, pageSize);
    }
}
