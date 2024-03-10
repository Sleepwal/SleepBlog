package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.entity.Chat;


/**
 * 聊天室(Chat)表服务接口
 *
 * @author SleepWalker
 * @since 2024-01-28 13:16:24
 */
public interface ChatService extends IService<Chat> {

    Result<?> getChatList();
}

