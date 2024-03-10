package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2022/12/18 17:34
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo<T> {
    private T rows;
    private Long total;
}

