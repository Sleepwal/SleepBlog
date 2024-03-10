package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleep.domain.Result;
import com.sleep.domain.entity.*;
import com.sleep.domain.vo.KnowledgeDetailVo;
import com.sleep.mapper.*;
import com.sleep.service.KnowledgeBaseService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.sleep.constants.MyConstants.CATEGORY_STATUS_NORMAL;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/30 10:16
 */

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private KnowCateMapper knowCateMapper;
    @Autowired
    private KnowDocumentMapper knowDocumentMapper;
    @Autowired
    private KnowArticleMapper knowArticleMapper;

    @Override
    public Result<?> getKnowledgeDetail(Long id) {
        //根据id查询分类
        LambdaQueryWrapper<KnowCate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowCate::getStatus, CATEGORY_STATUS_NORMAL);
        queryWrapper.eq(KnowCate::getId, id);
        KnowCate knowCate = knowCateMapper.selectOne(queryWrapper);

        //查询分类下的文档
        LambdaQueryWrapper<KnowDocument> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(KnowDocument::getCategoryId, knowCate.getId());
        List<KnowDocument> knowDocumentList = knowDocumentMapper.selectList(queryWrapper1);

        //查询文档下的文章
        knowDocumentList.forEach( knowDocument ->  {
            LambdaQueryWrapper<KnowArticle> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(KnowArticle::getStatus, CATEGORY_STATUS_NORMAL);
            queryWrapper2.eq(KnowArticle::getDocumentId, knowDocument.getId());
            List<KnowArticle> knowArticleList = knowArticleMapper.selectList(queryWrapper2);
            knowDocument.setChildren(knowArticleList);
        });

        //设置vo
        KnowledgeDetailVo knowledgeDetailVo = BeanCopyUtils.copyBean(knowCate, KnowledgeDetailVo.class);
        knowledgeDetailVo.setChildren(knowDocumentList);

        return Result.success(knowledgeDetailVo);
    }
}
