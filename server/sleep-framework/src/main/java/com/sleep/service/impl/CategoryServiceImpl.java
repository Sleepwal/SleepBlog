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
    public List<CategoryVo> CategoryList() {
        return doGetCategoryList(false);
    }

    /**
     * 所有类别列表，没有文章的类别也返回
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<List<CategoryVo>> allCategoryList() {
        return Result.success(doGetCategoryList(true));
    }


    /**
     * @param isAllCategory 是否需要全部分类（包括没有文章的分类）
     * @return {@link List}<{@link CategoryVo}>
     */
    private List<CategoryVo> doGetCategoryList(boolean isAllCategory) {
        //1.查询所有分类，status为0
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus, CATEGORY_STATUS_NORMAL);
        List<Category> categoryList = list(queryWrapper);

        //2.查询所有正式文章(状态为0)
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleMapper.selectList(articleLambdaQueryWrapper);

        //3.设置每个分类的文章数
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categoryList, CategoryVo.class);
        categoryVos.forEach(categoryVo -> {
            categoryVo.setValue(articleList.stream()
                    .filter(article -> Objects.equals(article.getCategoryId(), categoryVo.getId())).count()
            );
        });

        if(!isAllCategory) { //不需要全部分类
            return categoryVos.stream().filter(vo -> vo.getValue() > 0).collect(Collectors.toList());
        }

        return categoryVos;
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

