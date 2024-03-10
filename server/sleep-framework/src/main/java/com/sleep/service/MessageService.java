package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.admin.AddOrUpdateMessageDto;
import com.sleep.domain.entity.Message;


/**
 * 留言(Message)表服务接口
 *
 * @author SleepWalker
 * @since 2023-06-09 19:58:55
 */
public interface MessageService extends IService<Message> {

    Result<?> messageList();
    Result<?> messageListByPage(Integer pageNum, Integer size);
    Result<?> addMessage(AddOrUpdateMessageDto dto);
    Result<?> updateMessage(AddOrUpdateMessageDto dto);

    Result<?>  deleteMessage(Long messageId);
}

