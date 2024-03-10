package com.sleep.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/20 22:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddArticleDto {
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "正文不能为空")
    private String content;
    private String summary;
    @NotNull(message = "所属分类id不能为空")
    private Long categoryId;
    private String thumbnail;
    @NotNull(message = "是否置顶不能为空")
    private Long isTop;
    @NotNull(message = "是否允许评论不能为空")
    private Long isComment;
    private List<String> tags;
}
