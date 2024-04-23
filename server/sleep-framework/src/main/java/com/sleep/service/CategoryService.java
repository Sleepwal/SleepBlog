package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateCategoryDto;
import com.sleep.domain.entity.Category;
import com.sleep.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author SleepWalker
 * @since 2022-12-15 12:57:38
 */
public interface CategoryService extends IService<Category> {

    List<CategoryVo> CategoryList();

    Result<List<CategoryVo>> allCategoryList();

    Result<?> addOrUpdateCategory(AddOrUpdateCategoryDto categoryDto);

    Result<?> deleteCategoryById(Long id);
}

