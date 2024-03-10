package com.sleep.domain.vo.admin;

import com.sleep.domain.entity.Article;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 标签(Tag)表实体类
 *
 * @author SleepWalker
 * @since 2023-02-22 10:19:31
 */
@Data
public class TagListPageVo {
    private Long id;
    private String name;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private List<Article> articles;
}

