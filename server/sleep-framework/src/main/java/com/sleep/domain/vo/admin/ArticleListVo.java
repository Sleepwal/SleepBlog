package com.sleep.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/18 17:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    private Long categoryId;
    //所属分类名字
    private String categoryName;
    //标签名
    private List<String> tagNames;
    //缩略图
    private String thumbnail;
    //访问量
    private Long viewCount;
    private Date createTime;
}
