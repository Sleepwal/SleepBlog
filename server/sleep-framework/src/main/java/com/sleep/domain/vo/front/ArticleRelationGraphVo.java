package com.sleep.domain.vo.front;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/12/2 17:43
 */
@Data
@NoArgsConstructor
public class ArticleRelationGraphVo {
    private Long id;
    private String name;
    private String value;
    private Integer category;
    private Integer x;
    private Integer y;
    private ItemStyle itemStyle;

    public ArticleRelationGraphVo(Long id, String name, String value, Integer x, Integer y, ItemStyle itemStyle) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.x = x;
        this.y = y;
        this.itemStyle = itemStyle;
    }

    public ArticleRelationGraphVo(Long id, String name, String value, Integer category, Integer x, Integer y, ItemStyle itemStyle) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.category = category;
        this.x = x;
        this.y = y;
        this.itemStyle = itemStyle;
    }
}

