package com.sleep.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 15:35
 */

@Data
public class UserLoginDto {
    @NotBlank(message = "名字不能为空")
    private String username;

    @Length(min=4, max=16, message = "合法的密码的长度在4-16个字符之间")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @Length(min=5, max=5, message = "合法的验证码的长度为5个字符")
    private String code;
}
