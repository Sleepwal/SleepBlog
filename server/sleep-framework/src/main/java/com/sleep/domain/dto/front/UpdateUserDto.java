package com.sleep.domain.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {
    @NotNull(message = "用户id不能为空")
    private Long id;
    @NotBlank(message = "账号不能为空")
    private String username;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    private String oldPassword;
    private String newPassword;
    private String avatar;
    private String email;
    private String phone;
    @NotBlank(message = "性别不能为空")
    private String sex;  //用户性别（0男，1女，2未知）
    private String introduction; //个人简介
}

