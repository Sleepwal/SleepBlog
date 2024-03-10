package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/7 11:27
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
