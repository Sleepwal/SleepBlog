//package com.sleep.controller;
//
//import com.sleep.domain.Result;
//import com.sleep.domain.dto.admin.AddOrUpdateMessageDto;
//import com.sleep.service.MessageService;
//import io.swagger.models.auth.In;
//import kotlin.jvm.internal.Intrinsics;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Objects;
//
///**
// * @author SleepWalker
// * @version 1.0
// * @date 2023/6/9 20:01
// */
//@RestController
//@RequestMapping({"/admin/message"})
//public class MessageController {
//   @Autowired
//   private MessageService messageService;
//
//   @GetMapping({"/totalMessageCount"})
//   public Result<?> totalMessageCount() {
//      return Result.success(messageService.count());
//   }
//
//   @GetMapping({"/listPage"})
//   public Result<?> getMessageList(@NotNull Integer pageNum,
//                                   @NotNull Integer size) {
//      return messageService.messageListByPage(pageNum, size);
//   }
//
//   @PostMapping({"/addOrUpdateMessage"})
//   public Result addOrUpdateMessage(@Validated @RequestBody AddOrUpdateMessageDto dto) {
//      if (Objects.nonNull(dto.getUserId()))
//         return messageService.updateMessage(dto);
//      return messageService.addMessage(dto);
//   }
//
//   @DeleteMapping({"/deleteMessage/{id}"})
//   public Result deleteMessage(@PathVariable("id") @NotNull Long id) {
//      return messageService.deleteMessage(id);
//   }
//}
