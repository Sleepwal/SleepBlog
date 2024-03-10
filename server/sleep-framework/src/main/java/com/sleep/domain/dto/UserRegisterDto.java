package com.sleep.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 16:04
 */

@Data
public class UserRegisterDto {
    @NotBlank(message = "名字不能为空")
    private String username;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @Length(min=4, max=16, message = "合法的密码的长度在4-16个字符之间")
    private String password;
    @Email
    private String email;
    @Length(min=5, max=5, message = "合法的验证码的长度为5个字符")
    private String code;
}
