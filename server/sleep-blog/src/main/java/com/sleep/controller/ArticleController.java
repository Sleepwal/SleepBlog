package com.sleep.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleep.domain.Result;
import com.sleep.domain.entity.Article;
import com.sleep.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章控制器
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/7 11:36
 */

@Validated
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/test")
    public List<Article> test() {
        return articleService.list();
    }

    @ApiOperation("查询归档文章列表")
    @GetMapping("/timeLineArticleList")
    public Result<?> timeLineArticleList() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Article::getCreateTime);
        List<Article> articles = articleService.list(wrapper);
        return Result.success(articles);
    }

    @ApiOperation("查询热门文章")
    @GetMapping("/hotArticleList")
    public Result<?> hotArticleList() {
        return articleService.hotArticleList();
    }

    @ApiOperation("分页查询文章列表")
    @GetMapping("/articleListByPage")
    public Result<?> articleListByPage(@NotNull(message = "pageNum为空") Integer pageNum,
                                       @NotNull(message = "size为空") Integer size, Long categoryId) {
        return articleService.articleListByPage(pageNum, size, categoryId);
    }

    @ApiOperation("根据标签id查询文章")
    @GetMapping("/articleListByTag")
    public Result<?> articleListByTag(@NotNull(message = "pageNum为空") Integer pageNum,
                                       @NotNull(message = "size为空") Integer size,
                                      @NotNull(message = "标签id为空") Long tagId) {
        return articleService.articleListByTag(pageNum, size, tagId);
    }

    @ApiOperation("根据标题或内容查询文章")
    @GetMapping("/articleSearch")
    public Result<?> articleSearch(@NotBlank(message = "搜索文本不能为空") String searchText) {
        return articleService.articleSearch(searchText);
    }

    @ApiOperation("查询文章详情")
    @GetMapping("/articleDetail/{id}")
    public Result<?> articleDetail(@PathVariable("id") Long id) {
        return articleService.articleDetail(id);
    }

    @GetMapping("/articleAutoList")
    public Result<?> articleAutoList () {
        return articleService.articleAutoList();
    }

    @ApiOperation("更新文章浏览量")
    @PutMapping("/updateViewCount/{id}")
    public Result<?> updateViewCount(@PathVariable("id") Integer id) {
        return articleService.updateViewCount(id);
    }

    @ApiOperation("知识图谱数据")
    @GetMapping("/articleRelationGraph")
    public Result<?> articleRelationGraph() {
        return articleService.articleRelationGraph();
    }

    @ApiOperation("知识图谱图中链接")
    @GetMapping("/articleRelationGraphLinks")
    public Result<?> articleRelationGraphLinks() {
        return articleService.articleRelationGraphLinks();
    }

    @ApiOperation("单篇文章知识图谱数据")
    @GetMapping("/singleArticleGraphById/{id}")
    public Result<?> singleArticleGraphById(@NotNull(message = "文章id为空")
                                                @PathVariable("id") Integer id) {
        return articleService.singleArticleGraphById(id);
    }
}
