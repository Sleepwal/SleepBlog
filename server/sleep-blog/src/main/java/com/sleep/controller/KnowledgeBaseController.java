package com.sleep.controller;

import com.sleep.domain.Result;
import com.sleep.domain.entity.KnowArticle;
import com.sleep.service.KnowArticleService;
import com.sleep.service.KnowCateService;
import com.sleep.service.KnowledgeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 知识库控制器
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/30 10:14
 */
@Validated
@RestController
@RequestMapping("/knowledge")
public class KnowledgeBaseController {
    @Autowired
    private KnowledgeBaseService knowledgeBaseService;
    @Autowired
    private KnowArticleService knowArticleService;
    @Autowired
    private KnowCateService knowCateService;

    @GetMapping("/detail/{id}")
    public Result<?> getKnowledgeDetail(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        return knowledgeBaseService.getKnowledgeDetail(id);
    }

    @GetMapping("/articleDetail/{id}")
    public Result<?> getKnowArticleDetail(@NotNull(message = "id不能为空") @PathVariable("id") Long id) {
        return knowArticleService.getKnowArticleDetail(id);
    }

    @GetMapping("/cateList")
    public Result<?> getCateList() {
        return knowCateService.getCateList();
    }
}
