//package com.sleep.controller;
//
///**
// * @author SleepWalker
// * @version 1.0
// * @date 2023/12/5 13:34
// */
//
//import com.sleep.domain.Result;
//import com.sleep.domain.dto.admin.AddOrUpdateLinkDto;
//import com.sleep.service.LinkService;
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
///**
// * @author SleepWalker
// * @version 1.0
// * @date 2023/6/9 20:01
// */
//@RestController
//@RequestMapping({"/admin/link"})
//public class LinkController {
//    @Autowired
//    private LinkService linkService;
//
//    @GetMapping({"/linkListByPage"})
//    @NotNull
//    public Result<?> getLinkListByPage(int pageNum, int size) {
//        return linkService.getLinkListByPage(pageNum, size);
//    }
//
//    @PostMapping({"/addOrUpdateLink"})
//    @NotNull
//    public Result<?> addOrUpdateLink(@RequestBody @Validated @NotNull AddOrUpdateLinkDto dto) {
//        return linkService.addOrUpdateLink(dto);
//    }
//
//    @DeleteMapping({"/deleteLink/{id}"})
//    @NotNull
//    public Result<?> deleteLink(@PathVariable long id) {
//        return linkService.deleteLink(id);
//    }
//}
