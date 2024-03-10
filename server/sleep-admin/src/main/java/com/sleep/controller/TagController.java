package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.UpdateTagDto;
import com.sleep.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/22 10:22
 */

@RestController
@RequestMapping("/admin/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/getTagList")
    public Result<?> testList() {
        return tagService.getTagList();
    }

    @GetMapping("/tagListPage")
    public Result<?> tagListPage(Integer pageNum, Integer size) {
        return tagService.tagListPage(pageNum, size);
    }

    @PostMapping("/addOrUpdateTag")
    public Result<?> addOrUpdateTag(@RequestBody UpdateTagDto updateTagDto) {
        return tagService.addOrUpdateTag(updateTagDto);
    }

    @DeleteMapping("deleteTag/{id}")
    public Result<?> deleteTag(@PathVariable("id") Integer id) {
        return tagService.removeById(id) ? Result.success() : Result.fail(ResultEnum.DELETE_DATABASE_ERROR);
    }

}
