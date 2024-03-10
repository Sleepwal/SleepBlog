package com.sleep.domain.vo.admin;

import lombok.Data;

import java.util.Date;

/**
 * 评论(Comment)表实体类
 *
 * @author SleepWalker
 * @since 2023-07-18 16:27:36
 */

@Data
public class CommentListPageVo {
    private Long id;
    private Long articleId;
    private String articleTitle;
    private Long rootId;
    private String content;
    private Long replyCommentId;
    private String replyCommentUserName;
    private Long replyCommentUserId;
    private Long createBy;
    private Date createTime;
    private String nickname;
    private String avatar;
}

