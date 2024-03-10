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
public enum CommandType {
    CONNECTION(10001), // 建立连接
    CHAT(10002), // 聊天消息
    JOIN_GROUP(10003), //加入群聊
    ERROR(-1),
    ;
    private final Integer code;

    public static CommandType match(Integer code) {
        for (CommandType type : CommandType.values()) {
            if (type.getCode().equals(code))
                return type;
        }
        return ERROR;
    }
}
