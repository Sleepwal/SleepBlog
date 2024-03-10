package com.sleep.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 22:45
 */

@Data
public class ArticleAutoVo {
    private Long id;
    private String title;
    private String thumbnail;
}
