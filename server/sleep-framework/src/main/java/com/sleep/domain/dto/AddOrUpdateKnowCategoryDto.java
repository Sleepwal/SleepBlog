package com.sleep.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/17 21:06
 */

@Data
public class AddOrUpdateKnowCategoryDto {
    private Long id;
    @NotBlank(message = "分类名不能为空")
    private String title;
    private String description;
}
