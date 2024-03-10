package com.sleep.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/7/14 16:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorVo {
    private String nickname;
    private String avatar;
    private String sex;
    private Integer count;
    private String introduction;
}
