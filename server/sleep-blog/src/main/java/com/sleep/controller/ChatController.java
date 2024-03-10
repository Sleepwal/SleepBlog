package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.entity.Chat;
import com.sleep.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2024/1/28 13:33
 */

@Validated
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/getChatList")
    public Result<?> getChatList() {
        return chatService.getChatList();
    }

    @PostMapping("/addChatMessage")
    public Result addChatMessage(@RequestBody Chat chat) {
        chat.setCreateBy(chat.getId());
        boolean save = chatService.save(chat);
        if (!save) return Result.fail(ResultEnum.SAVE_DATABASE_ERROR);
        return Result.success();
    }

}
