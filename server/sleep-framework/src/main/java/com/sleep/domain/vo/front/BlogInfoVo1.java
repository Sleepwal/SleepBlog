package com.sleep.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/8/17 12:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogInfoVo1 {
    private int articleCount;
    private int categoryCount;
    private int tagCount;
    private int commentCount;
}
