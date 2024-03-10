package com.sleep.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 友链(Link)表实体类
 *
 * @author SleepWalker
 * @since 2022-12-19 20:50:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrUpdateLinkDto {
    private Long id;
    @NotBlank(message = "网站名为空")
    private String name;
    @NotBlank(message = "logo为空")
    private String logo;
    private String description;
    @NotBlank(message = "网站链接为空")
    private String address;
    @NotBlank(message = "状态为空")
    private String status;
}

