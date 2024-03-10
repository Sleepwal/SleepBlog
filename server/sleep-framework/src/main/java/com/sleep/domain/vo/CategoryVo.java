package com.sleep.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类表(Category)表VO类
 *
 * @author SleepWalker
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVo {
    private Long id;

    //分类名
    private String name;
    private Long value;
    private String description;
}

