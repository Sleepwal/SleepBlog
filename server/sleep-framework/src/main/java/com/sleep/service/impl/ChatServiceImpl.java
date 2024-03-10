package com.sleep.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.Result;
import com.sleep.domain.entity.Chat;
import com.sleep.domain.vo.front.ChatMessagesVo;
import com.sleep.mapper.ChatMapper;
import com.sleep.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天室(Chat)表服务实现类
 *
 * @author SleepWalker
 * @since 2024-01-28 13:16:24
 */
@Service("chatService")
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
    @Autowired
    private ChatMapper chatMapper;

    @Override
    public Result<?> getChatList() {
        List<ChatMessagesVo> list = chatMapper.selectAllChatMessages();
        return Result.success(list);
    }
}

