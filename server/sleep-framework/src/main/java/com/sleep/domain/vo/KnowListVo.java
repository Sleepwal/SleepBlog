package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/30 10:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowListVo {
    private Long id;
    private String title;
    private String description;
    private Integer value;
    private Long categoryId;
}
