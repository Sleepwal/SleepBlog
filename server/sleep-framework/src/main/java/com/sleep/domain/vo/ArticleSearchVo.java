package com.sleep.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/18 13:23
 */

@Data
public class ArticleSearchVo {
    private Long id;
    private String title;
    private String summary;
    private Long categoryId;
    private String categoryName;
    private String thumbnail;
//    private String isAuto;
    private Long isTop;
    //状态（0已发布，1草稿）
    private String status;
    private Long viewCount;
    //是否允许评论 1是，0否
    private Long isComment;
    private Date createTime;
    private Date updateTime;
    private List<String> tagNames;
}
