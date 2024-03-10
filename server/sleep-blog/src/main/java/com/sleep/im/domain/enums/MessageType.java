package com.sleep.im.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 17:11
 */

@Getter
@AllArgsConstructor
public enum MessageType {
    /**
     * 私聊
     */
    PRIVATE(1),
    /**
     * 群聊
     */
    GROUP(2),
    /**
     * 广播
     */
    BROADCAST(3),
    ERROR(-1);
    private final Integer type;

    public static MessageType match(Integer type) {
        for (MessageType value : MessageType.values()) {
            if (value.getType().equals(type))
                return value;
        }
        return ERROR;
    }
}
