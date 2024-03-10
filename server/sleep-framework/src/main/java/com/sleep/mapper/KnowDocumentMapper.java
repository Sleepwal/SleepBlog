package com.sleep.mapper;

import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.KnowDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * 知识库文档(KnowDocument)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-05-30 22:09:22
 */
@Mapper
public interface KnowDocumentMapper extends BaseMapper<KnowDocument> {

}

