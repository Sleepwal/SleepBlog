package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.constants.MyConstants;
import com.sleep.domain.Result;
import com.sleep.domain.entity.KnowArticle;
import com.sleep.mapper.KnowArticleMapper;
import com.sleep.service.KnowArticleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 知识库的文章(KnowArticle)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-05-30 22:08:36
 */
@Service("knowArticleService")
public class KnowArticleServiceImpl extends ServiceImpl<KnowArticleMapper, KnowArticle> implements KnowArticleService {

    @Override
    public Result<?> getKnowArticleDetail(Long id) {
        KnowArticle knowArticle = getById(id);
        return Result.success(knowArticle);
    }

    @Override
    public Result<?> articleList(Long documentId) {
        LambdaQueryWrapper<KnowArticle> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KnowArticle::getDocumentId, documentId)
                .eq(KnowArticle::getStatus, MyConstants.ARTICLE_STATUS_NORMAL);
        List<KnowArticle> list = list(wrapper);
        return Result.success(list);
    }
}

