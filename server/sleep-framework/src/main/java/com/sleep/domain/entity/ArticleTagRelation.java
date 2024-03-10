package com.sleep.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 文章和标签关系表(ArticleTagRelation)表实体类
 *
 * @author SleepWalker
 * @since 2023-07-14 21:05:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_tag_relation")
public class ArticleTagRelation  {
    @TableId
    private Long id;

    //文章id
    private Long articleId;
    //标签id
    private Long tagId;

}

