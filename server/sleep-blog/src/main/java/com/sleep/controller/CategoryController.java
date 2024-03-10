package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 分类表(Category)表控制层
 *
 * @author SleepWalker
 * @since 2022-12-18 14:30:32
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询文章类型")
    @GetMapping("/getCategoryList")
    public Result<?> CategoryList(){
        return categoryService.CategoryList();
    }

}

