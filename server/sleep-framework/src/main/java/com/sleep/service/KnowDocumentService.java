package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.AddOrUpdateKnowDocumentDto;
import com.sleep.domain.entity.KnowDocument;


/**
 * 知识库文档(KnowDocument)表服务接口
 *
 * @author SleepWalker
 * @since 2023-05-30 22:09:22
 */
public interface KnowDocumentService extends IService<KnowDocument> {

    Result<?> documentList();

    Result<?> addOrUpdateKnowDocument(AddOrUpdateKnowDocumentDto knowDocumentDto);

    Result<?> documentList4Chart();
}

