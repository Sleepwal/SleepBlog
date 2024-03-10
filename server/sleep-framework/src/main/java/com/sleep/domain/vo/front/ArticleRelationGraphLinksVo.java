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
public class ArticleRelationGraphLinksVo {
    private Long source;
    private Long target;
}