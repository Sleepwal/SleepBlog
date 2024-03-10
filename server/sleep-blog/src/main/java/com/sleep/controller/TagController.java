package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/7/14 21:11
 */

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/getTagList")
    public Result<?> getTagList() {
        return tagService.getTagList();
    }
}
