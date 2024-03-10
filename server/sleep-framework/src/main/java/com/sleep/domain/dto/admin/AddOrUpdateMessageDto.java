package com.sleep.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/9 20:19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrUpdateMessageDto {
    private Long id;
    @NotBlank(message = "留言内容不能为空")
    private String text;
    private Long userId;
}
