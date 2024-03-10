package com.sleep.im.handler;

import com.sleep.im.IMServer;
import com.sleep.im.domain.WSResult;
import com.sleep.im.domain.models.Command;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 17:21
 */

public class ConnectionHandler {
    public static void execute(ChannelHandlerContext ctx, Command command) {
        if (IMServer.USERS.containsKey(command.getNickname())) {
            ctx.channel().writeAndFlush(WSResult.fail("该用户已上线。"));
            return;
        }

        IMServer.USERS.put(command.getNickname(), ctx.channel());
        ctx.channel().writeAndFlush(WSResult.success("成功连接服务端!"));
    }
}
