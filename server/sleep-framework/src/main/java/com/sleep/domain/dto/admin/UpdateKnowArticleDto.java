package com.sleep.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/20 22:49
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKnowArticleDto {
    @NotNull
    private Long id;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "正文不能为空")
    private String content;
    @NotNull(message = "所属文档id不能为空")
    private Long documentId;
    private String summary;
    private String thumbnail;
}
