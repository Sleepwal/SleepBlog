package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddArticleDto;
import com.sleep.domain.dto.admin.UpdateArticleDto;
import com.sleep.domain.entity.Article;
import com.sleep.domain.vo.ArticleDetailVo;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/7 11:29
 */
public interface ArticleService extends IService<Article> {
    Result<?> hotArticleList();

    Result<?> articleListByPage(Integer pageNum, Integer size, Long categoryId);

    Result<?> articleDetail(Long id);

    Result<?> updateViewCount(Integer id);

    Result<?> articleAutoList();

    Result<?> articleSearchList(Integer pageNum, Integer size, String title, String status, Long categoryId);

    Result<?> addArticle(AddArticleDto addArticleDto);

    Result<?> editArticleDetail(Long id);

    Result<?> deleteArticle(Long id);

    Result<?> updateArticle(UpdateArticleDto addArticleDto);

    Result<?> articleList();

    Result<?> articleListByTag(Integer pageNum, Integer size, Long tagId);

    Result<?> articleSearch(String searchText);

    Result<?> articleRelationGraph();

    Result<?> articleRelationGraphLinks();

    Result<?> singleArticleGraphById(Integer id);

    ArticleDetailVo onlyArticleDetail(Long id);
}
