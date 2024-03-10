package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Chat;
import com.sleep.domain.vo.front.ChatMessagesVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 聊天室(Chat)表数据库访问层
 *
 * @author SleepWalker
 * @since 2024-01-28 13:16:23
 */
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {
    List<ChatMessagesVo> selectAllChatMessages();
}

