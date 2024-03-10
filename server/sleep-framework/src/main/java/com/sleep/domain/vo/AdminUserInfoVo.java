package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/2/22 21:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {
    //菜单
    private List<String> permissions;
    //角色
    private List<String> roles;
    private UserInfoVo user;
}
