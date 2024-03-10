package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.constants.MyConstants;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateKnowCategoryDto;
import com.sleep.domain.entity.KnowCate;
import com.sleep.domain.entity.KnowDocument;
import com.sleep.domain.vo.KnowListVo;
import com.sleep.mapper.KnowCateMapper;
import com.sleep.mapper.KnowDocumentMapper;
import com.sleep.service.KnowCateService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 知识库 文章类型(KnowCate)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-05-30 22:09:02
 */
@Service("knowCateService")
public class KnowCateServiceImpl extends ServiceImpl<KnowCateMapper, KnowCate> implements KnowCateService {
    @Autowired
    private KnowDocumentMapper knowDocumentMapper;

    @Override
    public Result<?> getCateList() {
        LambdaQueryWrapper<KnowCate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowCate::getStatus, MyConstants.CATEGORY_STATUS_NORMAL);
        List<KnowCate> knowCateList = list(queryWrapper);
        //转化vo
        List<KnowListVo> vos = BeanCopyUtils.copyBeanList(knowCateList, KnowListVo.class);
        //设置文档数
        vos.forEach(vo -> {
            LambdaQueryWrapper<KnowDocument> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(KnowDocument::getCategoryId, vo.getId());
            Integer count = knowDocumentMapper.selectCount(wrapper);
            vo.setValue(count);
        });

        return Result.success(vos);
    }

    @Override
    public Result<?> addOrUpdateCategory(AddOrUpdateKnowCategoryDto knowCategoryDto) {
        //1.查询有否已有该分类
        LambdaQueryWrapper<KnowCate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KnowCate::getId, knowCategoryDto.getId());
        KnowCate one = getOne(queryWrapper);

        KnowCate knowCate = BeanCopyUtils.copyBean(knowCategoryDto, KnowCate.class);

        //2.不存在就添加分类
        if(Objects.isNull(one)) {
            boolean save = save(knowCate);
            if (!save)
                return Result.fail(500, "添加分类失败");
            return Result.success("添加分类成功！");
        }

        //3.存在就修改分类
        boolean update = updateById(knowCate);
        if(!update)
            return Result.fail(500, "更新分类失败");
        return Result.success("更新分类成功！");
    }

}

