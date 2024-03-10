package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.admin.AddOrUpdateMessageDto;
import com.sleep.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/9 20:01
 */

@Validated
@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messageList")
    public Result<?> messageList() {
        return messageService.messageList();
    }

    @PostMapping("/addMessage")
    public Result<?> addMessage(@Validated @RequestBody AddOrUpdateMessageDto dto) {
        return messageService.addMessage(dto);
    }
}
