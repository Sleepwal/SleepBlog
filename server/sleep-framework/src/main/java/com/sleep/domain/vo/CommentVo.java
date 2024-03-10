package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/9 21:40
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long replyCommentId;
    //所回复的目标评论的用户名
    private String replyCommentUserName;

    //回复目标评论id
    private Long replyCommentUserId;

    private Long createBy;

    private Date createTime;
    //当前评论的用户名
    private String nickname;
    //用户头像
    private String avatar;

    private List<CommentVo> children;
}

