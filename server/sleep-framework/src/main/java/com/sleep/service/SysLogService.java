package com.sleep.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sleep.domain.Result;
import com.sleep.domain.entity.SysLog;


/**
 * 系统日志(SysLog)表服务接口
 *
 * @author SleepWalker
 * @since 2023-12-05 12:08:18
 */
public interface SysLogService extends IService<SysLog> {
    Result<?> logListByPage(Integer pageNum, Integer size);
}

