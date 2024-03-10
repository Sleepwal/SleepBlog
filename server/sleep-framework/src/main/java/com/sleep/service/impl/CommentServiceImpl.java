package com.sleep.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sleep.constants.MyConstants;
import com.sleep.domain.Result;
import com.sleep.domain.entity.Comment;
import com.sleep.domain.entity.User;
import com.sleep.domain.vo.CommentVo;
import com.sleep.domain.vo.PageVo;
import com.sleep.domain.vo.admin.CommentListPageVo;
import com.sleep.exception.SystemException;
import com.sleep.mapper.ArticleMapper;
import com.sleep.mapper.CommentMapper;
import com.sleep.mapper.UserMapper;
import com.sleep.service.CommentService;
import com.sleep.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.sleep.constants.MyConstants.ARTICLE_COMMENT;
import static com.sleep.domain.ResultEnum.CONTENT_IS_NULL;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author SleepWalker
 * @since 2023-02-09 21:31:29
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    ArticleMapper articleMapper;

    private static final Integer isRootId = -1;

    @Override
    public Result<?> commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //查询当前文章，文章评论才需要articleId
        queryWrapper.eq(ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);
        //查询该文章的根评论
        queryWrapper.eq(Comment::getRootId, isRootId);
        //评论类型
        queryWrapper.eq(Comment::getType, commentType);

        //分页
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<CommentVo> commentVos = replyCommentVoList(page.getRecords());

        //设置所有评论对应的子评论
        commentVos.stream()
                .forEach(commentVo -> commentVo.setChildren(doGetChildren(commentVo.getId())));

        PageVo<List<CommentVo>> pageVo = new PageVo<>(commentVos, page.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 查询rootId对应的子评论
     *
     * @author SleepWalker
     */
    private List<CommentVo> doGetChildren(Long rootId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, rootId);
        //按升序创建时间
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        return replyCommentVoList(comments);
    }

    /**
     * List中的元素转换成CommentVo
     *
     * @author SleepWalker
     */
    private List<CommentVo> replyCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        commentVos.stream()
                .forEach(vo -> {
                    //评论用户昵称,头像
                    User user = userMapper.selectById(vo.getCreateBy());
                    vo.setNickname(user.getNickname());
                    vo.setAvatar(user.getAvatar());

                    //回复用户昵称,如果有
                    if (!Objects.nonNull(vo.getReplyCommentId()) && !Objects.equals(vo.getReplyCommentUserId(), -1L)) {
                        String replyCommentUserName = userMapper.selectById(vo.getReplyCommentId()).getNickname();
                        vo.setReplyCommentUserName(replyCommentUserName);
                    }
                });

        return commentVos;
    }


    @Override
    public Result<?> addComment(Comment comment) {
        //评论内容不能空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(CONTENT_IS_NULL);
        }

        save(comment);
        return Result.success();
    }

    @Override
    public Result<?> getCommentListByPage(String commentType, int pageNum, int size) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //评论类型
        queryWrapper.eq(Comment::getType, commentType);
        Page<Comment> commentPage = new Page<>(pageNum, size);
        page(commentPage, queryWrapper);

        List<CommentVo> commentVos = replyCommentVoList(commentPage.getRecords());
        if (Objects.equals(commentType, MyConstants.LINK_COMMENT)) {
            PageVo<List<CommentVo>> pageVo = new PageVo<>(commentVos, commentPage.getTotal());
            return Result.success(pageVo);
        }

        //文章评论
        List<CommentListPageVo> listPageVos = BeanCopyUtils.copyBeanList(commentVos, CommentListPageVo.class);
        listPageVos.stream()
                .forEach(vo -> {
                    if (vo.getArticleId() != -1)
                        vo.setArticleTitle(articleMapper.selectById(vo.getArticleId()).getTitle());
                    if (vo.getReplyCommentUserId() != -1)
                        vo.setReplyCommentUserName(userMapper.selectById(vo.getReplyCommentUserId()).getNickname());
                });

        PageVo<List<CommentListPageVo>> pageVo = new PageVo<>(listPageVos, commentPage.getTotal());
        return Result.success(pageVo);
    }


}

