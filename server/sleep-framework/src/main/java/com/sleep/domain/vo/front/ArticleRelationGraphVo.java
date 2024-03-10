package com.sleep.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/12/2 17:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRelationGraphVo {
    private Long id;
    private String name;
    private String value;
    private Integer x;
    private Integer y;
    private ItemStyle itemStyle;
}

