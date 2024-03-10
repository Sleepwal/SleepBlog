package com.sleep.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.SysLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志(SysLog)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-12-05 12:08:17
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {

}

