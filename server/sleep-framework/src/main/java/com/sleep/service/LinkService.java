package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.dto.admin.AddOrUpdateLinkDto;
import com.sleep.domain.dto.front.LinkDto;
import com.sleep.domain.entity.Link;
import org.jetbrains.annotations.NotNull;


/**
 * 友链(Link)表服务接口
 *
 * @author SleepWalker
 * @since 2022-12-19 20:50:03
 */
public interface LinkService extends IService<Link> {

    Result<?> allLink();

    Result<?> applyLink(LinkDto linkDto);

    Result<?> getLinkListByPage(Integer pageNum, Integer size);

    Result<?> addOrUpdateLink(AddOrUpdateLinkDto dto);

    @NotNull
    Result<?> deleteLink(Long id);
}

