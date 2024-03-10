package com.sleep.im.domain;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 17:25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSResult {
    private String name;
    private String avatar;
    private String time;
    private String content;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间

    public static TextWebSocketFrame fail(String content) {
        return new TextWebSocketFrame(JSON.toJSONString(new WSResult("系统消息", "", sdf.format(new Date()), content)));
    }

    public static TextWebSocketFrame success(String content) {
        return new TextWebSocketFrame(JSON.toJSONString(new WSResult("系统消息", "", sdf.format(new Date()), content)));
    }

    public static TextWebSocketFrame success(String user, String avatar, String content) {
        return new TextWebSocketFrame(JSON.toJSONString(new WSResult(user, avatar, sdf.format(new Date()), content)));
    }
}
