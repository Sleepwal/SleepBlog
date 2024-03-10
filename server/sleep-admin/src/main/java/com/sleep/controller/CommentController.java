//package com.sleep.controller;
//
//
//import com.sleep.constants.MyConstants;
//import com.sleep.domain.Result;
//import com.sleep.domain.ResultEnum;
//import com.sleep.domain.entity.Comment;
//import com.sleep.service.CommentService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author  SleepWalker
// * @date  2023/7/24 13:11
// * @version 1.0
// */
//@RestController
//@RequestMapping("/admin/comment")
//class CommentController {
//    @Autowired
//    private CommentService commentService;
//
//    @GetMapping("/commentListByPage")
//    Result<?> getCommentListByPage(int pageNum, int size) {
//        return commentService.getCommentListByPage(MyConstants.ARTICLE_COMMENT, pageNum, size);
//    }
//
//    @GetMapping("/linkCommentListPage")
//    Result<?> getLinkCommentListPage(int pageNum, int size) {
//        return commentService.getCommentListByPage(MyConstants.LINK_COMMENT, pageNum, size);
//    }
//
//    @ApiOperation("评论总数")
//    @GetMapping("/count")
//    Result<?> getCommentCount() {
//        return Result.success(commentService.count());
//    }
//
//    @PostMapping("/updateComment")
//    Result<?> updateComment(@RequestBody Comment comment) {
//        boolean update = commentService.updateById(comment);
//        if(update)
//            return Result.success() ;
//        else
//            return Result.fail(ResultEnum.UPDATE_DATABASE_ERROR);
//    }
//
//    @DeleteMapping("/deleteComment/{id}")
//    Result<?> deleteComment(@PathVariable Long id) {
//        boolean remove = commentService.removeById(id);
//        if(remove) return Result.success();
//        else return Result.fail(ResultEnum.UPDATE_DATABASE_ERROR);
//    }
//
//}