package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.constants.CommonConstants;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.admin.AddOrUpdateLinkDto;
import com.sleep.domain.dto.front.LinkDto;
import com.sleep.domain.entity.Link;
import com.sleep.domain.vo.LinkVo;
import com.sleep.domain.vo.PageVo;
import com.sleep.mapper.LinkMapper;
import com.sleep.service.LinkService;
import com.sleep.utils.BeanCopyUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 友链(Link)表服务实现类
 *
 * @author SleepWalker
 * @since 2022-12-19 20:50:03
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public Result<?> allLink() {
        //1.审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, CommonConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(queryWrapper);

        //2.转换成VO
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);

        //返回
        return Result.success(linkVoList);
    }

    @Override
    public Result<?> applyLink(LinkDto linkDto) {
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        //状态为2, 后台审核过才能显示
        link.setStatus(CommonConstants.LINK_STATUS_AUDIT);
        boolean save = save(link);
        if(!save)
            return Result.fail(ResultEnum.SAVE_DATABASE_ERROR);

        return Result.success();
    }

    @Override
    public Result<?> getLinkListByPage(Integer pageNum, Integer size) {
        //1.审核通过的友链
        Page<Link> page = new Page<>(pageNum, size);
        page(page);

        PageVo<List<Link>> listPageVo = new PageVo<>(page.getRecords(), page.getTotal());

        return Result.success(listPageVo);
    }

    @Override
    public Result<?> addOrUpdateLink(AddOrUpdateLinkDto dto) {
        Link link = BeanCopyUtils.copyBean(dto, Link.class);
        if(Objects.isNull(link.getId())) { //id为空, 添加
            boolean save = save(link);
            return save ? Result.success() : Result.fail(ResultEnum.SAVE_DATABASE_ERROR);
        }
        //更新
        boolean update = updateById(link);
        return update ? Result.success() : Result.fail(ResultEnum.UPDATE_DATABASE_ERROR);
    }

    @NotNull
    @Override
    public Result<?> deleteLink(Long id) {
        return removeById(id) ? Result.success() : Result.fail(ResultEnum.DELETE_DATABASE_ERROR);
    }
}

