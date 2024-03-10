package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.constants.PatternConstants;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.AddArticleDto;
import com.sleep.domain.dto.admin.UpdateArticleDto;
import com.sleep.domain.entity.Article;
import com.sleep.domain.entity.ArticleTagRelation;
import com.sleep.domain.entity.Category;
import com.sleep.domain.entity.Tag;
import com.sleep.domain.vo.*;
import com.sleep.domain.vo.admin.ArticleListVo;
import com.sleep.domain.vo.admin.EditArticleDetailVo;
import com.sleep.domain.vo.front.ArticleRelationGraphLinksVo;
import com.sleep.domain.vo.front.ArticleRelationGraphVo;
import com.sleep.domain.vo.front.ItemStyle;
import com.sleep.domain.vo.front.SingleArticleGraphByIdVo;
import com.sleep.mapper.ArticleMapper;
import com.sleep.mapper.ArticleTagRelationMapper;
import com.sleep.mapper.CategoryMapper;
import com.sleep.mapper.TagMapper;
import com.sleep.service.ArticleService;
import com.sleep.utils.BeanCopyUtils;
import com.sleep.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.sleep.constants.MyConstants.*;
import static com.sleep.constants.SystemConstants.VIEW_COUNT_KEY;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/7 11:30
 */

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleTagRelationMapper articleTagRelationMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisCache redisCache;


    /**
     * 查询前10热门文章，封装ResponseResul返回
     *
     * @author SleepWalker
     */
    @Override
    public Result<?> hotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //正式文章，不是草稿
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        //按浏览量降序
        queryWrapper.orderByDesc(Article::getViewCount);
        //10篇
        Page<Article> page = this.page(new Page<>(DEFAULT_CURRENT_PAGE, DEFAULT_PAGE_SIZE), queryWrapper);

        List<Article> records = page.getRecords();
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(records, HotArticleVo.class);

        return Result.success(hotArticleVos);
    }

    /**
     * 分页查询文章列表
     *
     * @author SleepWalker
     */
    @Override
    public Result<?> articleListByPage(Integer pageNum, Integer size, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //1.正式文章，status为ARTICLE_STATUS_NORMAL
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL)
                //2.置顶文章放前面，isTop逆序排序
                .orderByDesc(Article::getIsTop, Article::getCreateTime)
                //3.有categoryId时，查询有categoryId的文章
                .eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);

        //3.分页
        Page<Article> page = new Page<>(pageNum, size);
        page(page, queryWrapper);

        //4.封装成VO
        //4.1 根据categoryId查到Name
        List<Article> articleList = page.getRecords();
        //4.2 复制属性
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        articleListVos.forEach(articleListVo -> {
            //根据categoryId查到Name
            articleListVo.setCategoryName(categoryMapper.selectById(articleListVo.getCategoryId()).getName());

            //根据 文章id 查询 tagIds
            /*List<ArticleTagRelation> tagRelations = articleTagRelationMapper.selectList(new LambdaQueryWrapper<ArticleTagRelation>()
                    .eq(ArticleTagRelation::getArticleId, articleListVo.getId()));

            //根据 tagIds 查询 name
            List<String> tagNames = tagRelations.stream()
                    .map(ArticleTagRelation::getTagId)
                    .map(t -> tagMapper.selectById(t).getName())
                    .collect(Collectors.toList());
            articleListVo.setTagNames(tagNames);*/
            articleListVo.setTagNames(doGetTagNamesByArticleId(articleListVo.getId()));
        });

        //5.封装PageVo
        PageVo<List<ArticleListVo>> pageVo = new PageVo<>(articleListVos, page.getTotal());

        //6.返回
        return Result.success(pageVo);
    }

    /**
     * 文章列表 标签
     *
     * @param pageNum 页面num
     * @param size    大小
     * @param tagId   标签id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleListByTag(Integer pageNum, Integer size, Long tagId) {
        //根据 tagId 查询 articleId 的列表
        LambdaQueryWrapper<ArticleTagRelation> articleTagRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagRelationLambdaQueryWrapper.eq(ArticleTagRelation::getTagId, tagId);
        List<ArticleTagRelation> articleTagRelations = articleTagRelationMapper.selectList(articleTagRelationLambdaQueryWrapper);
        List<Long> articleIds = articleTagRelations.stream()
                .map(ArticleTagRelation::getArticleId)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(articleIds))
            return Result.fail(ResultEnum.SELECT_DATABASE_EMPTY);

        //根据articleIds查询文章, 分页
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Article::getId, articleIds)
                .eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        Page<Article> page = new Page<>(pageNum, size);
        page(page, queryWrapper);

        //封装成VO
        List<Article> articleList = page.getRecords();
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        articleListVos.forEach(articleListVo -> {
            //根据categoryId查到Name
            articleListVo.setCategoryName(categoryMapper.selectById(articleListVo.getCategoryId()).getName());

            //根据 文章id 查询 tagIds
            List<ArticleTagRelation> tagRelations = articleTagRelationMapper.selectList(new LambdaQueryWrapper<ArticleTagRelation>()
                    .eq(ArticleTagRelation::getArticleId, articleListVo.getId()));

            //根据 tagIds 查询 name
            List<String> tagNames = tagRelations.stream()
                    .map(ArticleTagRelation::getTagId)
                    .map(t -> tagMapper.selectById(t).getName())
                    .collect(Collectors.toList());
            articleListVo.setTagNames(tagNames);
        });

        //封装PageVo
        PageVo<List<ArticleListVo>> pageVo = new PageVo<>(articleListVos, page.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 根据标题或内容 搜索文章
     *
     * @param searchText 搜索文本
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleSearch(String searchText) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Article::getTitle, searchText)
                .or().like(Article::getContent, searchText));

        List<Article> articles = list(wrapper);
        return Result.success(articles);
    }

    /**
     * 文章细节
     * 某篇文章的详情
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleDetail(Long id) {
        //1.根据id查询文章
        Article article = getById(id);

        //2.查询分类名
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            article.setCategoryName(category.getName());
        }

        //3.从redis中查询浏览量
        Integer viewCount = redisCache.getCacheMapValue(VIEW_COUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());

        //4.封装成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);

        //5.查询双链语法,替换
        Pattern pattern = Pattern.compile(PatternConstants.BI_DIRECTIONAL_LINKS);
        Matcher matcher = pattern.matcher(article.getContent());


        StringBuffer stringBuffer = new StringBuffer();
        List<DataPair> outDoubleLink = new ArrayList<>(); //出链列表

        while (matcher.find()) {
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            String group = matcher.group(1); //第一个匹配的串

            //根据双链内的文章名 查询id
            wrapper.eq(Article::getTitle, group);
            Article one = getOne(wrapper);
            if (one == null)
                continue;
            //替换
//            matcher.appendReplacement(stringBuffer, "<p><a class=\"tooltip\" href=\"http://localhost:5173/ArticleDetail/" + one.getId() + "\">" +
//                    "<span>$1</span>" +
//                    "<span class=\"tooltiptext\"><iframe src=\"http://localhost:5173/OnlyArticle/" + one.getId() + "\" width=\"800\" height=\"450\"></iframe></span>" +
//                    "</a></p>");
            matcher.appendReplacement(stringBuffer, "<p><a class=\"tooltip\" href=\"http://localhost:5173/ArticleDetail/" + one.getId() + "\">" +
                    "<span>$1</span>" +
                    "<span class=\"tooltiptext\"><a href=\"http://localhost:5173/OnlyArticle/" + one.getId() + "\" width=\"800\" height=\"450\"></a></span>" +
                    "</a></p>");

            outDoubleLink.add(new DataPair(one.getId(), group)); //添加到出链
        }
        matcher.appendTail(stringBuffer);
        articleDetailVo.setContent(stringBuffer.toString());
        articleDetailVo.setOutDoubleLink(outDoubleLink);

        //根据 文章id 查询 tagIds
        /*List<ArticleTagRelation> tagRelations = articleTagRelationMapper.selectList(new LambdaQueryWrapper<ArticleTagRelation>()
                .eq(ArticleTagRelation::getArticleId, articleDetailVo.getId()));

        //根据 tagIds 查询 name
        List<String> tagNames = tagRelations.stream()
                .map(ArticleTagRelation::getTagId)
                .map(t -> tagMapper.selectById(t).getName())
                .collect(Collectors.toList());
        articleDetailVo.setTagNames(tagNames);*/
        articleDetailVo.setTagNames(doGetTagNamesByArticleId(articleDetailVo.getId()));

        //返回
        return Result.success(articleDetailVo);
    }

    /**
     * 后台编辑文章详情
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> editArticleDetail(Long id) {
        EditArticleDetailVo editArticleDetailVo = BeanCopyUtils.copyBean(getById(id), EditArticleDetailVo.class);
        //对应标签名
        editArticleDetailVo.setTags(doGetTagNamesByArticleId(editArticleDetailVo.getId()));

        return Result.success(editArticleDetailVo);
    }

    /**
     * 根据 文章id 查询 标签列表
     *
     * @param articleId 文章id
     * @return {@link List}<{@link String}>
     */
    private List<String> doGetTagNamesByArticleId(Long articleId) {
        //关联表查询 tagIds
        List<ArticleTagRelation> tagRelations = articleTagRelationMapper.selectList(new LambdaQueryWrapper<ArticleTagRelation>()
                .eq(ArticleTagRelation::getArticleId, articleId));

        //根据 tagIds 查询 name
        return tagRelations.stream()
                .map(ArticleTagRelation::getTagId)
                .map(t -> tagMapper.selectById(t).getName())
                .collect(Collectors.toList());
    }

    /**
     * 删除文章
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> deleteArticle(Long id) {
        boolean remove = removeById(id);
        if (!remove)
            return Result.fail(ResultEnum.DELETE_ARTICLE_ERROR);
        return Result.success();
    }

    /**
     * 更新浏览量数
     *
     * @param id id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> updateViewCount(Integer id) {
        //浏览量加1
        redisCache.increaseViewCount(VIEW_COUNT_KEY, id, 1);

        return Result.success();
    }

    /**
     * 首页轮播文章列表
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleAutoList() {
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Article::getIsAuto, IS_AUTO_ARTICLE); //轮播文章
//        List<Article> articleList = list(queryWrapper);
//        //转化成VO
//        List<ArticleAutoVo> articleAutoVos = BeanCopyUtils.copyBeanList(articleList, ArticleAutoVo.class);
//        return Result.success(articleAutoVos);
        return null;
    }

    /**
     * 文章搜索列表
     *
     * @param title      标题
     * @param status     状态
     * @param categoryId 类别id
     * @param pageNum    页面num
     * @param size       大小
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleSearchList(Integer pageNum, Integer size, String title, String status, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //1.有值时，添加条件
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId)
                .like(StringUtils.hasText(title), Article::getTitle, title)
                .eq(StringUtils.hasText(status), Article::getStatus, status);

        //2.分页
        Page<Article> page = new Page<>(pageNum, size);
        page(page, queryWrapper);

        //3.封装成VO
        List<Article> articleList = page.getRecords();
        //分类名
        articleList.stream()
                .forEach(article -> article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName()));
        List<ArticleSearchVo> articleSearchVoList = BeanCopyUtils.copyBeanList(articleList, ArticleSearchVo.class);

        //标签名
        articleSearchVoList.forEach(vo -> {
            LambdaQueryWrapper<ArticleTagRelation> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ArticleTagRelation::getArticleId, vo.getId());
            List<Long> tagIds = articleTagRelationMapper.selectList(wrapper).stream()
                    .map(ArticleTagRelation::getTagId).collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(tagIds)) { //有对应标签
                List<String> tags = tagMapper.selectBatchIds(tagIds).stream()
                        .map(Tag::getName).collect(Collectors.toList());

                vo.setTagNames(tags);
            }
        });

        //4.封装PageVo
        PageVo<List<ArticleSearchVo>> pageVo = new PageVo<>(articleSearchVoList, page.getTotal());

        //6.返回
        return Result.success(pageVo);
    }

    /**
     * 添加文章
     *
     * @param addArticleDto 添加文章dto
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        article.setStatus("0");
        boolean save = save(article);
        if (!save)
            return Result.fail(ResultEnum.ADD_ARTICLE_ERROR);

        //标签
        //查询标签是否存在
        List<String> tags = addArticleDto.getTags();
        tags.stream()
                .forEach(tag -> {
                    LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(Tag::getName, tag);
                    Tag one = tagMapper.selectOne(wrapper);

                    //不存在添加tag表, relation表
                    if (Objects.isNull(one)) {
                        Tag t = new Tag();
                        t.setName(tag);
                        tagMapper.insert(t);

                        ArticleTagRelation relation = new ArticleTagRelation();
                        relation.setTagId(t.getId());
                        relation.setArticleId(article.getId());
                        articleTagRelationMapper.insert(relation);
                    } else { //存在就添加relation表
                        ArticleTagRelation relation = new ArticleTagRelation();
                        relation.setTagId(one.getId());
                        relation.setArticleId(article.getId());
                        articleTagRelationMapper.insert(relation);
                    }
                });

        return Result.success();
    }

    /**
     * 更新文章
     *
     * @param updateArticleDto 更新文章dto
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> updateArticle(UpdateArticleDto updateArticleDto) {
        Article article = BeanCopyUtils.copyBean(updateArticleDto, Article.class);
        boolean update = updateById(article);
        if (!update)
            Result.fail(ResultEnum.UPDATE_ARTICLE_ERROR);

        updateArticleTags(updateArticleDto.getTags(), article.getId());

        return Result.success();
    }

    /**
     * 更新文章的标签
     *
     * @param tags 标签
     */
    private void updateArticleTags(List<String> tags, Long articleId) {
        if (CollectionUtils.isEmpty(tags)) return; //为空返回

        //删除关联表中 文章对应的标签
        LambdaQueryWrapper<ArticleTagRelation> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(ArticleTagRelation::getArticleId, articleId);
        List<ArticleTagRelation> deleteList = articleTagRelationMapper.selectList(deleteWrapper);
        List<Long> deleteIdList = deleteList.stream()
                .map(ArticleTagRelation::getId)
                .collect(Collectors.toList());
        articleTagRelationMapper.deleteBatchIds(deleteIdList);

        //查询标签是否存在
        tags.stream()
                .forEach(tag -> {
                    LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(Tag::getName, tag);
                    Tag one = tagMapper.selectOne(wrapper);

                    //标签不存在
                    if (Objects.isNull(one)) {
                        //插入tag表
                        Tag t = new Tag();
                        t.setName(tag);
                        tagMapper.insert(t);

                        //插入relation表
                        ArticleTagRelation relation = new ArticleTagRelation();
                        relation.setTagId(t.getId());
                        relation.setArticleId(articleId);
                        articleTagRelationMapper.insert(relation);
                    } else { //标签存在
                        //查询relation表是否已关联
                        LambdaQueryWrapper<ArticleTagRelation> relationWrapper = new LambdaQueryWrapper<>();
                        relationWrapper.eq(ArticleTagRelation::getArticleId, articleId)
                                .eq(ArticleTagRelation::getTagId, one.getId());
                        ArticleTagRelation relationOne = articleTagRelationMapper.selectOne(relationWrapper);

                        //relation表中没有
                        if (Objects.isNull(relationOne)) {
                            //插入relation表
                            ArticleTagRelation relation = new ArticleTagRelation();
                            relation.setTagId(one.getId());
                            relation.setArticleId(articleId);
                            articleTagRelationMapper.insert(relation);
                        }
                    }
                });

    }

    /**
     * 文章列表
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //1.正式文章，status为ARTICLE_STATUS_NORMAL
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = list(queryWrapper);

        //2.封装成VO
        //2.1 根据categoryId查到Name
        articleList.stream()
                .forEach(article -> article.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName()));
        //2.2 复制属性
        List<ArticleListVo> articleListVoList = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);

        //3.返回
        return Result.success(articleListVoList);
    }


    /**
     * 生成随机颜色的方法
     *
     * @return {@link Color}
     */
    private static Color generateRandomColor() {
        Random random = new Random();
        // 生成随机的RGB值
        int red = random.nextInt(256); // 0-255
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // 使用RGB值创建颜色对象
        return new Color(red, green, blue);
    }


    /**
     * 将RGB值转换为十六进制
     *
     * @return {@link String}
     */
    private static String convertToHex(Color color) {
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }


    /**
     * 知识图谱数据
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> articleRelationGraph() {
        List<ArticleRelationGraphVo> voList = new ArrayList<>();
        Map<Long, String> colorMap = new HashMap<>();

        //查询所有文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        List<Article> articleList = list(queryWrapper);

        //随机数
        int min = 1, max = 100;
        Random random = new Random();

        //设置vo
        articleList.forEach(article -> {
            int randomX = random.nextInt(max - min + 1) + min;
            int randomY = random.nextInt(max - min + 1) + min;

            if (Objects.isNull(colorMap.get(article.getCategoryId()))) //该类型没有添加过颜色
                colorMap.put(article.getCategoryId(), convertToHex(generateRandomColor())); // 生成随机颜色

            String name = categoryMapper.selectById(article.getCategoryId()).getName();

            ArticleRelationGraphVo graphVo = new ArticleRelationGraphVo(article.getId(), name, article.getTitle(),
                    randomX, randomY,
                    new ItemStyle(colorMap.get(article.getCategoryId()))
            );
            voList.add(graphVo);
        });

        return Result.success(voList);
    }

    @Override
    public Result<?> articleRelationGraphLinks() {
        List<ArticleRelationGraphLinksVo> Links = new ArrayList<>();

        //查询双链语法
        Pattern pattern = Pattern.compile(PatternConstants.BI_DIRECTIONAL_LINKS);

        //所有文章
        List<Article> articleList = list();
        articleList.forEach(article -> {
            Matcher matcher = pattern.matcher(article.getContent());
            while (matcher.find()) { //文章有双链
                LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
                String group = matcher.group(1); //第一个匹配的串

                //根据双链内的文章名 查询id
                wrapper.eq(Article::getTitle, group);
                Article one = getOne(wrapper);
                if (one == null) continue;

                //添加到links中
                ArticleRelationGraphLinksVo link = new ArticleRelationGraphLinksVo(article.getId(), one.getId());
                Links.add(link);
            }
        });

        return Result.success(Links);
    }

    @Override
    public Result<?> singleArticleGraphById(Integer id) {
        Set<Long> idSet = new HashSet<>();
        List<ArticleRelationGraphVo> nodes = new ArrayList<>();
        List<ArticleRelationGraphLinksVo> links = new ArrayList<>();
        Map<Long, String> colorMap = new HashMap<>();
        int min = 1, max = 100;
        Random random = new Random();

        //1.根据id查询文章
        Article article = getById(id);

        if (Objects.isNull(colorMap.get(article.getCategoryId()))) //该类型没有添加过颜色
            colorMap.put(article.getCategoryId(), convertToHex(generateRandomColor())); // 生成随机颜色

        //添加当前文章到nodes中
        ArticleRelationGraphVo curArticle = new ArticleRelationGraphVo(article.getId(),
                categoryMapper.selectById(article.getCategoryId()).getName(),
                article.getTitle(),
                random.nextInt(max - min + 1) + min,
                random.nextInt(max - min + 1) + min,
                new ItemStyle(colorMap.get(article.getCategoryId()))
        );
        idSet.add(curArticle.getId());
        nodes.add(curArticle);

        //2.搜索出链
        //2.1 查询双链语法
        Pattern pattern = Pattern.compile(PatternConstants.BI_DIRECTIONAL_LINKS);
        Matcher matcher = pattern.matcher(article.getContent());
        while (matcher.find()) { //文章有双链
            LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
            String group = matcher.group(1); //第一个匹配的串

            //根据双链内的文章名 查询出链文章
            wrapper.eq(Article::getTitle, group);
            Article one = getOne(wrapper);
            if (one == null) continue;

            if (Objects.isNull(colorMap.get(one.getCategoryId()))) //该类型没有添加过颜色
                colorMap.put(one.getCategoryId(), convertToHex(generateRandomColor())); // 生成随机颜色
            String name = categoryMapper.selectById(one.getCategoryId()).getName(); //分类名

            ArticleRelationGraphVo outLinkArticle = new ArticleRelationGraphVo(one.getId(),
                    name, one.getTitle(),
                    random.nextInt(max - min + 1) + min,
                    random.nextInt(max - min + 1) + min,
                    new ItemStyle(colorMap.get(one.getCategoryId()))
            );
            if (!idSet.contains(outLinkArticle.getId())) { //没有添加过
                idSet.add(outLinkArticle.getId());
                nodes.add(outLinkArticle); //添加出链文章到nodes中
            }

            //添加到links中
            ArticleRelationGraphLinksVo outLink = new ArticleRelationGraphLinksVo(article.getId(), one.getId());
            links.add(outLink);
        }

        //3.搜索入链
        List<Article> articles = list();
        //3.1 从所有文章中查询
        Pattern inLinkPattern = Pattern.compile(PatternConstants.BI_DIRECTIONAL_LINKS_PREFIX + article.getTitle() + PatternConstants.BI_DIRECTIONAL_LINKS_SUFFIX);
//        System.out.println("=========================" + inLinkPattern);

        articles.forEach(a -> {
            Matcher inLinkMatcher = inLinkPattern.matcher(a.getContent());
            if (inLinkMatcher.find()) { //匹配成功
//                System.out.println("===================================" + inLinkMatcher);

                if (Objects.isNull(colorMap.get(a.getCategoryId()))) //该类型没有添加过颜色
                    colorMap.put(a.getCategoryId(), convertToHex(generateRandomColor())); // 生成随机颜色
                String name = categoryMapper.selectById(a.getCategoryId()).getName(); //分类名

                ArticleRelationGraphVo inLinkArticle = new ArticleRelationGraphVo(a.getId(), name, a.getTitle(),
                        random.nextInt(max - min + 1) + min,
                        random.nextInt(max - min + 1) + min,
                        new ItemStyle(colorMap.get(a.getCategoryId()))
                );
                if (!idSet.contains(inLinkArticle.getId())) { //没有添加过
                    idSet.add(inLinkArticle.getId());
                    nodes.add(inLinkArticle); //添加出链文章到nodes中
                }

                //添加到links中
                ArticleRelationGraphLinksVo inLink = new ArticleRelationGraphLinksVo(a.getId(), article.getId());
                links.add(inLink);
            }
        });

        return Result.success(new SingleArticleGraphByIdVo(nodes, links));
    }
}
