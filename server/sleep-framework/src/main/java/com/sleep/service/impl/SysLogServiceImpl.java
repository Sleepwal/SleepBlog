package com.sleep.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.Result;
import com.sleep.domain.entity.SysLog;
import com.sleep.domain.vo.PageVo;
import com.sleep.mapper.SysLogMapper;
import com.sleep.service.SysLogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/12/5 13:06
 */

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Override
    public Result<?> logListByPage(Integer pageNum, Integer size) {
        Page<SysLog> page = new Page<>(pageNum, size);
        page(page);

        PageVo<List<SysLog>> listPageVo = new PageVo<>(page.getRecords(), page.getTotal());

        return Result.success(listPageVo);
    }
}
