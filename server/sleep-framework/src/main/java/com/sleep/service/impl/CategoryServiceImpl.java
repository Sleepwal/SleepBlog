package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateCategoryDto;
import com.sleep.domain.entity.Article;
import com.sleep.domain.entity.Category;
import com.sleep.domain.vo.CategoryVo;
import com.sleep.mapper.ArticleMapper;
import com.sleep.mapper.CategoryMapper;
import com.sleep.service.CategoryService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.sleep.constants.MyConstants.ARTICLE_STATUS_NORMAL;
import static com.sleep.constants.MyConstants.CATEGORY_STATUS_NORMAL;

/**
 * 分类表(Category)表服务实现类
 *
 * @author SleepWalker
 * @since 2022-12-15 12:57:38
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    ArticleMapper articleMapper;

    /**
     * 类别列表，没有文章的类别不要
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> CategoryList() {
        //1.查出正式文章(状态为0)的类型id
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleMapper.selectList(articleLambdaQueryWrapper);

        //2.类型id去重
        List<Long> idList = articleList.stream()
                .map(Article::getCategoryId)
                .distinct()
                .collect(Collectors.toList()); //toSet就不用distinct

        //3.查找类型id对应的类型名称，status为0
        List<Category> categoryList = listByIds(idList).stream()
                .filter(category -> CATEGORY_STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());

        //设置每个分类有value篇文章
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        categoryVos.stream().forEach(categoryVo -> {
            categoryVo.setValue((long)articleList.stream()
                    .filter(article -> Objects.equals(article.getCategoryId(), categoryVo.getId())).count()
            );
        });
        //4.封装VO，返回
        return Result.success(categoryVos);
    }

    /**
     * 所有类别列表，没有文章的类别也返回
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> allCategoryList() {
        //1.查找所有分裂，status为0
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, CATEGORY_STATUS_NORMAL);
        List<Category> categoryList = list(queryWrapper);

        //2.查出正式文章(状态为0)的类型id
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleMapper.selectList(articleLambdaQueryWrapper);

        //设置每个分类有value篇文章
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        categoryVos.stream().forEach(categoryVo -> {
            categoryVo.setValue(articleList.stream()
                    .filter(article -> Objects.equals(article.getCategoryId(), categoryVo.getId())).count()
            );
        });
        //4.封装VO，返回
        return Result.success(categoryVos);
    }

    @Override
    public Result<?> addOrUpdateCategory(AddOrUpdateCategoryDto categoryDto) {
        //1.查询有否已有该分类
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId, categoryDto.getId());
        Category one = getOne(queryWrapper);

        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);

        //2.不存在就添加分类
        if(Objects.isNull(one)) {
            boolean save = save(category);
            if (!save)
                return Result.fail(500, "添加分类失败");
            return Result.success("添加分类成功！");
        }

        //3.存在就修改分类
        boolean update = updateById(category);
        if(!update)
            return Result.fail(500, "更新分类失败");
        return Result.success("更新分类成功！");
    }

    /**
     * 根据ID删除类别
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> deleteCategoryById(Long id) {
        boolean remove = removeById(id);
        if(!remove)
            return Result.fail(500, "删除分类失败");
        return Result.success("删除分类成功！");
    }
}

