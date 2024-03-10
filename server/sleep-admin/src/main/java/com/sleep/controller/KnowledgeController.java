package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.AddOrUpdateKnowCategoryDto;
import com.sleep.domain.dto.AddOrUpdateKnowDocumentDto;
import com.sleep.domain.dto.admin.AddKnowArticleDto;
import com.sleep.domain.dto.admin.UpdateKnowArticleDto;
import com.sleep.domain.entity.KnowArticle;
import com.sleep.service.KnowArticleService;
import com.sleep.service.KnowCateService;
import com.sleep.service.KnowDocumentService;
import com.sleep.utils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/10 10:58
 */

@Validated
@RestController
@RequestMapping("/admin/knowledge")
public class KnowledgeController {
    @Autowired
    private KnowCateService knowCateService;
    @Autowired
    private KnowDocumentService knowDocumentService;
    @Autowired
    private KnowArticleService knowArticleService;

    //分类
    @GetMapping("/cateList")
    public Result<?> cateList() {
        return knowCateService.getCateList();
    }

    @GetMapping("/documentList4Chart")
    public Result<?> documentList4Chart() {
      return knowDocumentService.documentList4Chart();
    }

    @PostMapping("/addOrUpdateKnowCategory")
    public Result<?> addOrUpdateCategory(@Validated @RequestBody AddOrUpdateKnowCategoryDto knowCategoryDto) {
        return knowCateService.addOrUpdateCategory(knowCategoryDto);
    }

    @DeleteMapping("/deleteKnowCategory/{id}")
    public Result<?> deleteCategory(@NotNull @PathVariable("id") Long id) {
        boolean remove = knowCateService.removeById(id);
        if(!remove)
            return Result.fail(500, "删除失败");
        return Result.success();
    }

    @ApiOperation("获取文档列表")
    @GetMapping("/documentList")
    public Result<?> documentList() {
        return knowDocumentService.documentList();
    }

    @PostMapping("/addOrUpdateKnowDocument")
    public Result<?> addOrUpdateKnowDocument(@Validated @RequestBody AddOrUpdateKnowDocumentDto knowDocumentDto) {
        return knowDocumentService.addOrUpdateKnowDocument(knowDocumentDto);
    }

    @DeleteMapping("/deleteKnowDocument/{id}")
    public Result<?> deleteKnowDocument(@NotNull @PathVariable("id") Long id) {
        boolean remove = knowDocumentService.removeById(id);
        if(!remove)
            return Result.fail(500, "删除失败");
        return Result.success();
    }

    //文章
    @ApiOperation("根据文档查询文章")
    @GetMapping("/articleList/{documentId}")
    public Result<?> articleList(@NotNull(message = "类型id不能为空")
                                 @PathVariable("documentId")
                                     Long documentId) {
        return knowArticleService.articleList(documentId);
    }

    @ApiOperation("根据文章id查询文章详情")
    @GetMapping("/knowArticleDetail/{articleId}")
    public Result<?> knowArticleDetail(@NotNull(message = "文章id不能为空")
                                       @PathVariable("articleId")
                                       Long articleId) {
        return knowArticleService.getKnowArticleDetail(articleId);
    }

    @ApiOperation("添加知识库文章")
    @PostMapping("/addKnowArticle")
    public Result<?> updateKnowArticle(@Validated @RequestBody AddKnowArticleDto dto) {
        KnowArticle knowArticle = BeanCopyUtils.copyBean(dto, KnowArticle.class);
        boolean save = knowArticleService.save(knowArticle);
        if(!save)
            return Result.fail(ResultEnum.SAVE_DATABASE_ERROR);
        return Result.success();
    }

    @ApiOperation("更新知识库文章")
    @PutMapping("/updateKnowArticle")
    public Result<?> updateKnowArticle(@Validated @RequestBody UpdateKnowArticleDto dto) {
        KnowArticle knowArticle = BeanCopyUtils.copyBean(dto, KnowArticle.class);
        knowArticle.setStatus("0");
        boolean update = knowArticleService.updateById(knowArticle);
        if(!update)
            return Result.fail(ResultEnum.UPDATE_DATABASE_ERROR);
        return Result.success();
    }

    @ApiOperation("删除知识库文章")
    @DeleteMapping("/deleteKnowArticle/{articleId}")
    public Result<?> deleteKnowArticle(@NotNull(message = "文章id不能为空")
                                       @PathVariable("articleId")
                                       Long articleId) {
        boolean remove = knowArticleService.removeById(articleId);
        if(!remove)
            return Result.fail(ResultEnum.DELETE_DATABASE_ERROR);
        return Result.success();
    }

    @GetMapping("/totalArticleViewCount")
    public Result<?> totalArticleViewCount() {
        return Result.success(knowArticleService.count());
    }
}
