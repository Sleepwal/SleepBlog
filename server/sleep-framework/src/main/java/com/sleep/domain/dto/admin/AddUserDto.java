package com.sleep.domain.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户表(User)表实体类
 *
 * @author SleepWalker
 * @since 2023-01-31 22:14:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    @NotBlank(message = "用户账号不能为空")
    private String username;
    @NotBlank(message = "用户昵称不能为空")
    private String nickname;
    @NotBlank(message = "用户密码不能为空")
    private String password;
    @NotBlank(message = "用户类型不能为空")
    private String type;
    @NotBlank(message = "用户邮箱不能为空")
    private String email;
    @NotBlank(message = "用户性别不能为空")

    private String sex;
    private String avatar;
    private String phone;
    private String introduction;
}

