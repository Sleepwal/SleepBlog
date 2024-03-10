package com.sleep.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.domain.dto.admin.AddOrUpdateMessageDto;
import com.sleep.domain.entity.Message;
import com.sleep.domain.entity.User;
import com.sleep.domain.vo.MessageListVo;
import com.sleep.domain.vo.PageVo;
import com.sleep.mapper.MessageMapper;
import com.sleep.mapper.UserMapper;
import com.sleep.service.MessageService;
import com.sleep.utils.BeanCopyUtils;
import com.sleep.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 留言(Message)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-06-09 19:58:55
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<?> messageList() {
        //1.查询所有留言
        List<Message> messages = list();
        return Result.success(toMessageVos(messages));
    }

    /**
     * 分页查询留言
     *
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> messageListByPage(Integer pageNum, Integer size) {
        Page<Message> page = new Page<>(pageNum, size);
        page(page);

        List<MessageListVo> messageVos = toMessageVos(page.getRecords());

        PageVo<List<MessageListVo>> listPageVo = new PageVo<>(messageVos, page.getTotal());
        return Result.success(listPageVo);
    }

    /**
     * 转化成留言VO
     *
     * @param messages 消息
     * @return {@link List}<{@link MessageListVo}>
     */
    private List<MessageListVo> toMessageVos(List<Message> messages) {
        List<MessageListVo> messageListVos = BeanCopyUtils.copyBeanList(messages, MessageListVo.class);

        //根据用户id，查询用户名
        messageListVos.forEach( vo -> {
            User user = userMapper.selectById(vo.getUserId());
            vo.setNickname(user.getNickname());
            vo.setAvatar(user.getAvatar());
        });

        return messageListVos;
    }

    /**
     * 添加留言
     *
     * @param dto dto
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> addMessage(AddOrUpdateMessageDto dto) {
        Message message = BeanCopyUtils.copyBean(dto, Message.class);
        message.setUserId(SecurityUtils.getUserId());
        message.setCreateBy(SecurityUtils.getUserId());

        boolean save = save(message);
        if(!save)
            return Result.fail(500, "添加留言失败");
        return Result.success();
    }

    @Override
    public Result<?> updateMessage(AddOrUpdateMessageDto dto) {
        Message message = BeanCopyUtils.copyBean(dto, Message.class);
        boolean update = updateById(message);

        if(!update) return Result.fail(ResultEnum.DELETE_DATABASE_ERROR);
        return Result.success();
    }


    /**
     * 删除留言
     *
     * @param messageId 消息id
     * @return {@link Result}<{@link ?}>
     */
    @Override
    public Result<?> deleteMessage(Long messageId) {
        boolean remove = removeById(messageId);

        if(!remove) return Result.fail(ResultEnum.DELETE_DATABASE_ERROR);
        return Result.success();
    }
}

