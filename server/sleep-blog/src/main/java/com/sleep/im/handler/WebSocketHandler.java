package com.sleep.im.handler;

import com.alibaba.fastjson.JSON;
import com.sleep.im.domain.WSResult;
import com.sleep.im.domain.enums.CommandType;
import com.sleep.im.domain.models.Command;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 17:04
 */

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChatHandler chatHandler = new ChatHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        Command command = JSON.parseObject(frame.text(), Command.class);

        switch (CommandType.match(command.getCode())) {
            case CONNECTION:
                ConnectionHandler.execute(ctx, command);
                break;
            case CHAT:
                chatHandler.execute(ctx, frame);
                break;
            case JOIN_GROUP:
                JoinGroupHandler.execute(ctx);
                break;
            default:
                ctx.channel().writeAndFlush(WSResult.fail("不支持的CODE"));
        }

    }
}
