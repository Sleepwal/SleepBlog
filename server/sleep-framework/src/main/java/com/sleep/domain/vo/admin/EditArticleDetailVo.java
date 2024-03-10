package com.sleep.domain.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/19 10:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditArticleDetailVo {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String thumbnail;
    private Long isTop;
    private String status;
    private Long isComment;
    private List<String> tags;
}
