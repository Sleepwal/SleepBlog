package com.sleep.domain.vo.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/12/3 9:48
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleArticleGraphByIdVo {
    private List<ArticleRelationGraphVo> nodes;
    private List<ArticleRelationGraphLinksVo> links;
}
