package com.sleep.service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.entity.KnowArticle;


/**
 * 知识库的文章(KnowArticle)表服务接口
 *
 * @author SleepWalker
 * @since 2023-05-30 22:08:36
 */
public interface KnowArticleService extends IService<KnowArticle> {

    Result<?> getKnowArticleDetail(Long id);

    Result<?> articleList(Long documentId);
}

