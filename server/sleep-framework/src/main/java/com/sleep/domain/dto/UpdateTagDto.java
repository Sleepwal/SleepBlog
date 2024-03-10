package com.sleep.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/25 11:18
 */
@Data
public class UpdateTagDto {
    private Long id;
    @NotBlank(message = "标签名为空")
    private String name;
    private String remark;
}
