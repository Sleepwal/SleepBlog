package com.sleep.im.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 17:11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Command {
    private Integer code; //连接信息码
    private String nickname; //昵称
}
