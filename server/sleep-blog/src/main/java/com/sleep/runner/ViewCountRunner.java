package com.sleep.runner;

import com.sleep.constants.SystemConstants;
import com.sleep.domain.entity.Article;
import com.sleep.mapper.ArticleMapper;
import com.sleep.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //1.从数据库中读取浏览量
        List<Article> articles = articleMapper.selectList(null);
        //id为key，浏览量为value
        Map<String, Integer> map = articles.stream()
                .collect(Collectors.toMap(article -> {
                    return article.getId().toString();
                }, article -> {
                    return article.getViewCount().intValue();
                }));

        //2.将浏览量存入redis
        redisCache.setCacheMap(SystemConstants.VIEW_COUNT_KEY, map);
    }
}
