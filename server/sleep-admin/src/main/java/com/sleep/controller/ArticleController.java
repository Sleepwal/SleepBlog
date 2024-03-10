package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.dto.AddArticleDto;
import com.sleep.domain.dto.admin.UpdateArticleDto;
import com.sleep.domain.entity.Article;
import com.sleep.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import kotlin.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/18 10:03
 */

@Validated
@RestController
@RequestMapping("/admin/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 查询文章列表，模糊搜索
     *
     * @param pageNum    页面num
     * @param size       大小
     * @param title      标题
     * @param status     状态
     * @param categoryId 类别id
     * @return {@link Result}<{@link ?}>
     */
    @ApiOperation("分页查询文章列表，模糊搜索")
    @GetMapping("/articleSearchList")
    public Result<?> articleSearchList(@NotNull(message = "pageNum为空") Integer pageNum,
                                       @NotNull(message = "size为空") Integer size,
                                       String title, String status, Long categoryId) {
        return articleService.articleSearchList(pageNum, size, title, status, categoryId);
    }

    @ApiOperation("添加新文章")
    @PostMapping("/addArticle")
    public Result<?> addArticle(@Validated @RequestBody AddArticleDto addArticleDto) {
        return articleService.addArticle(addArticleDto);
    }

    @ApiOperation("更新文章")
    @PutMapping("/updateArticle")
    public Result<?> updateArticle(@Validated @RequestBody UpdateArticleDto updateArticleDto) {
        return articleService.updateArticle(updateArticleDto);
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/deleteArticle/{id}")
    public Result<?> deleteArticle(@NotNull(message = "文章id为空") @PathVariable("id") Long id) {
        return articleService.deleteArticle(id);
    }


    @ApiOperation("查询编辑文章详情")
    @GetMapping("/editArticleDetail/{id}")
    public Result<?> editArticleDetail(@NotNull(message = "文章id为空") @PathVariable("id") Long id) {
        return articleService.editArticleDetail(id);
    }

    @ApiOperation("分页查询文章列表")
    @GetMapping("/articleListByPage")
    public Result<?> articleListByPage(@NotNull(message = "pageNum为空") Integer pageNum,
                                       @NotNull(message = "size为空") Integer size, Long categoryId) {
        return articleService.articleListByPage(pageNum, size, categoryId);
    }

    @GetMapping("/articleList")
    public Result<?> articleList() {
        return articleService.articleList();
    }

    @ApiOperation("文章总数")
    @GetMapping("/articleCount")
    public Result<?> articleCount() {
        return Result.success(articleService.count());
    }

    @ApiOperation("所有文章总浏览量")
    @GetMapping("/articleTotalViewCount")
    public Result<?> articleTotalViewCount() {
        List<Article> list = articleService.list();
        Long cnt = 0L;
        for (Article l : list) {
            cnt += l.getViewCount();
        }
        return Result.success(cnt);
    }

    @ApiOperation("知识图谱数据")
    @GetMapping("/relationGraphData")
    public Result<?> articleRelationGraph() {
        return articleService.articleRelationGraph();
    }

    @ApiOperation("知识图谱图中链接")
    @GetMapping("/relationGraphLinks")
    public Result<?> articleRelationGraphLinks() {
        return articleService.articleRelationGraphLinks();
    }

    @ApiOperation("文章活跃度")
    @GetMapping("/articleActivity")
    public Result<?> articleActivity() {
        List<Date> times = articleService.list().stream()
                .map(Article::getCreateTime)
                .collect(Collectors.toList());

        Map<Pair<String, String>, Integer> map = new HashMap<>();
        times.forEach(time -> {
            // 获取月份
            SimpleDateFormat monthFormat = new SimpleDateFormat("M");
            String month = monthFormat.format(time);

            // 其中1表示星期一，2表示星期二，以此类推，7表示星期日。
            SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("u");
            Integer dayOfWeek = 7 - Integer.parseInt(dayOfWeekFormat.format(time));

            Pair<String, String> item = new Pair<>(month, dayOfWeek.toString());

            map.compute(item, (k, v) -> {
                if (v == null) {
                    return 1;
                }
                return ++v;
            });
        });

        List<List<String>> res = new ArrayList<>();
        map.forEach((key, val) -> {
            res.add(List.of(key.getFirst(), key.getSecond(), val.toString()));
        });

        return Result.success(res);
    }
}
