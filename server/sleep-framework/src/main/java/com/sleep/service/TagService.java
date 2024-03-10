package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.UpdateTagDto;
import com.sleep.domain.entity.Tag;


/**
 * 标签(Tag)表服务接口
 *
 * @author SleepWalker
 * @since 2023-07-14 21:07:31
 */
public interface TagService extends IService<Tag> {

    Result<?> getTagList();

    Result<?> tagListPage(Integer pageNum, Integer pageSize);

    Result<?> addOrUpdateTag(UpdateTagDto updateTagDto);
}

