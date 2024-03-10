package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateKnowCategoryDto;
import com.sleep.domain.entity.KnowCate;


/**
 * 知识库 文章类型(KnowCate)表服务接口
 *
 * @author SleepWalker
 * @since 2023-05-30 22:09:02
 */
public interface KnowCateService extends IService<KnowCate> {

    Result<?> getCateList();

    Result<?> addOrUpdateCategory(AddOrUpdateKnowCategoryDto categoryDto);
}

