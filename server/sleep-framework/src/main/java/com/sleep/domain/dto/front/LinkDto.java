package com.sleep.domain.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/19 20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkDto {
    @NotBlank(message = "名字不能为空")
    private String name;
    @NotBlank(message = "logo不能为空")
    private String logo;
    @NotBlank(message = "描述不能为空")
    private String description;
    @NotBlank(message = "网址不能为空")
    private String address;
}
