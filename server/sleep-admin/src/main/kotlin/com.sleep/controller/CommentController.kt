package com.sleep.controller

import com.sleep.constants.MyConstants
import com.sleep.domain.Result
import com.sleep.domain.ResultEnum
import com.sleep.domain.entity.Comment
import com.sleep.service.CommentService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*


/**
 * @author  SleepWalker
 * @date  2023/7/24 13:11
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/comment")
open class CommentController(private val commentService: CommentService) {
    @GetMapping("/commentListByPage")
    open fun getCommentListByPage(pageNum:Int, size:Int): Result<*> = commentService.getCommentListByPage(MyConstants.ARTICLE_COMMENT, pageNum, size)

    @GetMapping("/linkCommentListPage")
    open fun getLinkCommentListPage(pageNum:Int, size:Int): Result<*> = commentService.getCommentListByPage(MyConstants.LINK_COMMENT, pageNum, size)

    @ApiOperation("评论总数")
    @GetMapping("/count")
    open fun getCommentCount(): Result<*> = Result.success(commentService.count())

    @PostMapping("/updateComment")
    open fun updateComment(@RequestBody comment: Comment): Result<*> {
        val update = commentService.updateById(comment)
        return if(update) Result.success() else return Result.fail(ResultEnum.UPDATE_DATABASE_ERROR)
    }

    @DeleteMapping("/deleteComment/{id}")
    open fun deleteComment(@PathVariable id:Long): Result<*> {
        val remove = commentService.removeById(id)
        return if(remove) Result.success() else return Result.fail(ResultEnum.UPDATE_DATABASE_ERROR)
    }

}