package com.sleep.im.handler;

import com.alibaba.fastjson.JSON;
import com.sleep.im.IMServer;
import com.sleep.im.domain.WSResult;
import com.sleep.im.domain.enums.MessageType;
import com.sleep.im.domain.models.ChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/26 20:18
 */

public class ChatHandler {
    public void execute(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            ChatMessage msg = JSON.parseObject(frame.text(), ChatMessage.class);
            switch (MessageType.match(msg.getType())) {
                case PRIVATE:
                    if (StringUtil.isNullOrEmpty(msg.getTarget())) {
                        ctx.channel().writeAndFlush(WSResult.fail("请指定发送对象"));
                        return;
                    }

                    Channel channel = IMServer.USERS.get(msg.getTarget());
                    if (Objects.isNull(channel) || !channel.isActive()) {
                        ctx.channel().writeAndFlush(WSResult.fail("消息发送失败，" + msg.getTarget() + "不在线"));
                        return;
                    }

                    ctx.channel().writeAndFlush(WSResult.success("(私聊消息)" + msg.getNickname() + ": " + msg.getContent()));
                    break;
                case GROUP:
                case BROADCAST:
                    String avatar = new ChatHandler().doGetAvatar(msg.getId());
                    IMServer.GROUP.writeAndFlush(WSResult.success(msg.getNickname(), avatar, msg.getContent()));
                    break;
                default:
                    ctx.channel().writeAndFlush(WSResult.fail("不支持的消息类型"));
            }
        } catch (Exception e) {
            ctx.channel().writeAndFlush(WSResult.fail("发送的消息格式错误"));
        }
    }

    public String URL = "jdbc:mysql://localhost:3306/sleep_arsenal" + "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    public String USER = "root";
    public String PW = "166";

    private String doGetAvatar(Long id) {
        try {
            //1：加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2：获得数据库连接
            Connection conn = DriverManager.getConnection(URL, USER, PW);

            String sql = "select * from user where id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next())
                return rs.getString("avatar");

            rs.close();
        } catch (Exception e) {
            System.out.println("连接数据库时发生异常.异常信息为：" + e);
            return "default";
        }
        return "default";
    }
}
