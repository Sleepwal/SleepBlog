package com.sleep.service;

import com.sleep.domain.Result;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/30 10:15
 */

public interface KnowledgeBaseService {
    Result<?> getKnowledgeDetail(Long id);
}
