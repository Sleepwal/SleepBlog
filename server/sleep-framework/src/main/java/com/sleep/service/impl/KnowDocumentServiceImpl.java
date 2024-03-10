package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateKnowDocumentDto;
import com.sleep.domain.entity.KnowArticle;
import com.sleep.domain.entity.KnowDocument;
import com.sleep.domain.vo.ChartsVo;
import com.sleep.domain.vo.KnowListVo;
import com.sleep.mapper.KnowArticleMapper;
import com.sleep.mapper.KnowDocumentMapper;
import com.sleep.service.KnowDocumentService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 知识库文档(KnowDocument)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-05-30 22:09:22
 */
@Service("knowDocumentService")
public class KnowDocumentServiceImpl extends ServiceImpl<KnowDocumentMapper, KnowDocument> implements KnowDocumentService {
    @Autowired
    private KnowArticleMapper knowArticleMapper;
    @Override
    public Result<?> documentList() {
        List<KnowDocument> list = list();
        List<KnowListVo> vos = BeanCopyUtils.copyBeanList(list, KnowListVo.class);
        //查询文章数
        vos.forEach( vo -> {
            LambdaQueryWrapper<KnowArticle> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(KnowArticle::getDocumentId, vo.getId());
            vo.setValue(knowArticleMapper.selectCount(wrapper));
        });

        return Result.success(vos);
    }

    @Override
    public Result<?> documentList4Chart() {
        List<KnowDocument> list = list();
        List<ChartsVo> chartsVos = new ArrayList<>();

        list.forEach( document -> {
            ChartsVo chartsVo = new ChartsVo();
            chartsVo.setName(document.getTitle());

            LambdaQueryWrapper<KnowArticle> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(KnowArticle::getDocumentId, document.getId());
            chartsVo.setValue(knowArticleMapper.selectCount(wrapper));

            chartsVos.add(chartsVo);
        });

        return Result.success(chartsVos);
    }

    @Override
    public Result<?> addOrUpdateKnowDocument(AddOrUpdateKnowDocumentDto knowDocumentDto) {
        //1.查询有否已有该文档
        LambdaQueryWrapper<KnowDocument> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowDocument::getId, knowDocumentDto.getId());
        KnowDocument one = getOne(queryWrapper);

        KnowDocument knowDocument = BeanCopyUtils.copyBean(knowDocumentDto, KnowDocument.class);

        //2.不存在就添加文档
        if(Objects.isNull(one)) {
            boolean save = save(knowDocument);
            if (!save)
                return Result.fail(500, "添加分类失败");
            return Result.success("添加分类成功！");
        }

        //3.存在就修改文档
        boolean update = updateById(knowDocument);
        if(!update)
            return Result.fail(500, "更新分类失败");
        return Result.success("更新分类成功！");
    }
}

