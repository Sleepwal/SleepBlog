package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/9 20:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageListVo {
    private Long id;
    //留言内容
    private String text;
    //留言的用户id
    private Long userId;
    private String nickname;
    private String avatar;
    private Date createTime;
    private Date updateTime;
}
