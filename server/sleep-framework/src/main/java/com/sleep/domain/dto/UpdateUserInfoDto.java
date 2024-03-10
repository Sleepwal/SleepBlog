package com.sleep.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoDto {
    @NotNull(message = "用户id不能为空")
    private Long id;
    @NotBlank(message = "用户账号不能为空")
    private String username;
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;
    @NotBlank(message = "邮箱不能为空")
    private String email;
    private String introduction;
}

