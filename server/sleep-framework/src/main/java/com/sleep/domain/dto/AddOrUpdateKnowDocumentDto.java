package com.sleep.domain.dto;

import lombok.Data;
import org.aspectj.bridge.MessageWriter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/17 21:06
 */

@Data
public class AddOrUpdateKnowDocumentDto {
    private Long id;
    @NotBlank(message = "分类名不能为空")
    private String title;
    private String description;
    @NotNull(message="分类id不能为空")
    private Long categoryId;
}
