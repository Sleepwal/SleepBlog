package com.sleep.im.handler;


import com.sleep.im.IMServer;
import com.sleep.im.domain.WSResult;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 17:21
 */

public class JoinGroupHandler {
    public static void execute(ChannelHandlerContext ctx) {
        IMServer.GROUP.add(ctx.channel());
        ctx.channel().writeAndFlush(WSResult.success("已加入系统默认群聊~"));
    }
}
