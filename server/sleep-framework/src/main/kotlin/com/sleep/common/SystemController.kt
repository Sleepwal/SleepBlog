package com.sleep.common

import com.sleep.constants.SystemConstants
import com.sleep.domain.Result
import com.sleep.domain.vo.front.BlogInfoVo
import com.sleep.service.ArticleService
import com.sleep.service.CategoryService
import com.sleep.service.CommentService
import com.sleep.service.TagService
import com.sleep.utils.RedisCache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/7/15 16:06
 */
@RestController
@RequestMapping("/system")
open class SystemController {
    @Autowired
    private val redisCache: RedisCache? = null

    @Autowired
    private val articleService: ArticleService? = null

    @Autowired
    private val categoryService: CategoryService? = null

    @Autowired
    private val tagService: TagService? = null

    @Autowired
    private val commentService: CommentService? = null

    @GetMapping("/totalViewCount")
    open fun totalViewCount(): Result<*> {
        val map = redisCache!!.getCacheMap<Int>(SystemConstants.VIEW_COUNT_KEY)
        //求和
        val sum = map.values.stream()
            .collect(Collectors.summarizingInt { obj: Int -> obj })
            .sum
        return Result.success(sum)
    }

    @GetMapping("/increaseAccessCount")
    open fun accessCount(): Result<*> {
        var accessCount: Int? = redisCache?.getCacheObject<Int>(SystemConstants.USER_ACCESS_COUNT)
        if (accessCount != null)
            redisCache?.setCacheObject(SystemConstants.USER_ACCESS_COUNT, accessCount + 1)
        else
            redisCache?.setCacheObject(SystemConstants.USER_ACCESS_COUNT, 1)

        return Result.success()
    }

    @GetMapping("/getAccessCount")
    open fun getAccessCount(): Result<*> {
        var accessCount: Int? = redisCache?.getCacheObject<Int>(SystemConstants.USER_ACCESS_COUNT)

        return Result.success(accessCount)
    }


    @GetMapping("/blogInfoCount")
    open fun blogInfoCount(): Result<*> = Result.success(
        BlogInfoVo(
            articleService?.count(),
            categoryService?.count(),
            tagService?.count(),
            commentService?.count()
        )
    )

}
