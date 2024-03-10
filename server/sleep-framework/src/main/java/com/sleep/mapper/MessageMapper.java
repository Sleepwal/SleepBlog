package com.sleep.mapper;

import java.util.Date;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sleep.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 留言(Message)表数据库访问层
 *
 * @author SleepWalker
 * @since 2023-06-09 19:58:54
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}

