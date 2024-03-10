package com.sleep.domain.vo;

import com.sleep.domain.entity.KnowDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/30 10:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDetailVo {
    private String title;
    private String description;
    private List<KnowDocument> children;
}
