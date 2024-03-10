package com.sleep.domain.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/9 20:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageDto {
    @NotBlank(message = "留言内容不能为空")
    private String text;
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
