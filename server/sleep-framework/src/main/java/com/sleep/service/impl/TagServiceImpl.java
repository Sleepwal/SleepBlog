package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.UpdateTagDto;
import com.sleep.domain.entity.Article;
import com.sleep.domain.entity.ArticleTagRelation;
import com.sleep.domain.entity.Tag;
import com.sleep.domain.vo.PageVo;
import com.sleep.domain.vo.admin.TagListPageVo;
import com.sleep.domain.vo.front.TagVo;
import com.sleep.mapper.ArticleMapper;
import com.sleep.mapper.ArticleTagRelationMapper;
import com.sleep.mapper.TagMapper;
import com.sleep.service.TagService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 标签(Tag)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-07-14 21:07:32
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleTagRelationMapper articleTagRelationMapper;

    @Override
    public Result<?> getTagList() {
        List<Tag> tags = list();
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        tagVos.forEach(tagVo -> {
            //根据 tagId 找 articleId 数量
            LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTagRelation::getTagId, tagVo.getId());
            Long count = Long.valueOf(articleTagRelationMapper.selectCount(wrapper));
            tagVo.setValue(count);
        });
        return Result.success(tagVos);
    }

    @Override
    public Result<?> tagListPage(Integer pageNum, Integer pageSize) {
        Page<Tag> tagPage = new Page<>(pageNum, pageSize);
        page(tagPage);

        List<TagListPageVo> listPageVos = BeanCopyUtils.copyBeanList(tagPage.getRecords(), TagListPageVo.class);
        listPageVos.stream()
                .forEach(vo -> {
                    //查询对应的所有文章id
                    LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(ArticleTagRelation::getTagId, vo.getId());
                    List<ArticleTagRelation> relations = articleTagRelationMapper.selectList(wrapper);

                    //根据文章ids查询title
                    List<Long> ids = relations.stream()
                            .map(ArticleTagRelation::getArticleId)
                            .collect(Collectors.toList());
                    if(!CollectionUtils.isEmpty(ids)) {
                        List<Article> articles = articleMapper.selectBatchIds(ids);
                        //设置
                        vo.setArticles(articles);
                    }
                });

        PageVo<List<TagListPageVo>> listPageVo = new PageVo<>(listPageVos, tagPage.getTotal());

        return Result.success(listPageVo);
    }

    @Override
    public Result<?> addOrUpdateTag(UpdateTagDto updateTagDto) {
        Tag tag = BeanCopyUtils.copyBean(updateTagDto, Tag.class);
        if(Objects.isNull(tag.getId())) { //id为空, 添加
            boolean save = save(tag);
            return save ? Result.success() : Result.fail(ResultEnum.SAVE_DATABASE_ERROR);
        }
        //更新
        boolean update = updateById(tag);
        return update ? Result.success() : Result.fail(ResultEnum.UPDATE_DATABASE_ERROR);
    }
}

