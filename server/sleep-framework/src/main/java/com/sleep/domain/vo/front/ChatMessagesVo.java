package com.sleep.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/28 13:56
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagesVo {
    private String name;
    private String avatar;
    private String content;
    private Date time;
}
