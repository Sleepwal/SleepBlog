package com.sleep.controller

import com.sleep.domain.Result
import com.sleep.domain.dto.admin.AddOrUpdateMessageDto
import com.sleep.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/9 20:01
 */
@RestController
@RequestMapping("/admin/message")
open class MessageController {
    @Autowired
    private val messageService: MessageService? = null
    @GetMapping("/totalMessageCount")
    open fun totalMessageCount(): Result<*> {
        return Result.success(messageService!!.count())
    }

    @GetMapping("/listPage")
    open fun getMessageList(pageNum:Int, size:Int): Result<*> = messageService!!.messageListByPage(pageNum, size)

    @PostMapping("/addOrUpdateMessage")
    open fun addOrUpdateMessage(@Validated @RequestBody dto: AddOrUpdateMessageDto):Result<*> {
        if(dto.userId != null)
            return messageService!!.updateMessage(dto)

       return messageService!!.addMessage(dto)
    }

    @DeleteMapping("/deleteMessage/{id}")
    open fun deleteMessage(@PathVariable("id") id:Long):Result<*> =
        messageService!!.deleteMessage(id)
}
