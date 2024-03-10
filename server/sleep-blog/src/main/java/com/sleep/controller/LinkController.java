package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.front.LinkDto;
import com.sleep.service.LinkService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *  友链表(Link)表控制层
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/19 20:51
 */

@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    LinkService linkService;

    @ApiOperation("获取所有友链")
    @GetMapping("/getAllLink")
    public Result<?> getAllLink() {
        return linkService.allLink();
    }

    @PostMapping("/applyLink")
    public Result<?> applyLink(@RequestBody @Validated LinkDto linkDto) {
        return linkService.applyLink(linkDto);
    }

}
