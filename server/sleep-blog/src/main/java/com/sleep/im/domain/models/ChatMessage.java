package com.sleep.im.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 20:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    private Long id;
    private String nickname;
    private Integer type; //消息类型
    private String target; //目标用户
    private String content; // 内容
}
