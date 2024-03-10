package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateCategoryDto;
import com.sleep.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


/**
 * 类别控制器
 * 分类表(Category)表控制层
 *
 * @author manson
 * @date 2023/05/16
 */

@Validated
@RestController
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation("查询所有文章类型")
    @GetMapping("/allCategoryList")
    public Result<?> allCategoryList() {
        return categoryService.allCategoryList();
    }

    @ApiOperation("查询有文章的类型")
    @GetMapping("/categoryList")
    public Result<?> categoryList() {
        return categoryService.CategoryList();
    }

    @ApiOperation("添加或修改分类")
    @PostMapping("/addOrUpdateCategory")
    public Result<?> addOrUpdateCategory(@Validated @RequestBody AddOrUpdateCategoryDto categoryDto) {
        return categoryService.addOrUpdateCategory(categoryDto);
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/deleteCategory/{id}")
    public Result<?> deleteCategory(@NotNull @PathVariable("id") Long id) {
        return categoryService.deleteCategoryById(id);
    }

    @ApiOperation("分类总数")
    @GetMapping("/categoryTotalCount")
    public Result<?> categoryTotalCount() {
        return Result.success(categoryService.count());
    }
}

