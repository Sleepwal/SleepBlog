package com.sleep.domain.dto.admin;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/20 22:49
 */

@Data
public class AddKnowArticleDto {
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "正文不能为空")
    private String content;
    @NotNull(message = "所属文档id不能为空")
    private Long documentId;
    private String summary;
    private String thumbnail;
}
