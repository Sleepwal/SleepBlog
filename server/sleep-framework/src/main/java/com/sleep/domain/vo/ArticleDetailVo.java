package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/19 10:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    private String title; //标题
    private String content;  //文章内容
    private String thumbnail; //缩略图
    private Long categoryId;   //所属分类id
    private String categoryName; //所属分类名字
    private List<String> tagNames; //标签名列表
    private Long viewCount; //访问量
    private String isComment; //是否允许评论 1是，0否
    private Date createTime;
    private List<DataPair> outDoubleLink; //出链列表
}
