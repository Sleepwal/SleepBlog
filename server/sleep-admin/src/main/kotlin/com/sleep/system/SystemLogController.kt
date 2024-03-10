package com.sleep.system

import com.sleep.domain.Result
import com.sleep.domain.ResultEnum
import com.sleep.service.SysLogService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*


/**
 * @author  SleepWalker
 * @date  2023/12/5 15:19
 * @version 1.0
 */
@RestController
@RequestMapping("/admin/sysLog")
open class SystemLogController(private val systemLogService: SysLogService) {
    @ApiOperation("查询系统日志")
    @GetMapping("/logListByPage")
    open fun getSystemLogList(pageNum:Int, size:Int): Result<*> = systemLogService.logListByPage(pageNum, size)

    @ApiOperation("删除日志")
    @DeleteMapping("/deleteSystemLog/{id}")
    open fun deleteSystemLog(@PathVariable id:Long):Result<*>  {
        return if(systemLogService.removeById(id)) Result.success() else return Result.fail(ResultEnum.UPDATE_DATABASE_ERROR)
    }

    @ApiOperation("活跃人数统计")
    @GetMapping("/getHourlyActivity")
    open fun getHourlyActivity(): Result<*> {
        var list = systemLogService.list().map { l -> l.createTime }
        // 创建一个数组来存储每个小时的活跃人数
        val hourlyActivity = IntArray(24)

        // 遍历活跃人数记录，统计每个小时的活跃人数
        for (record in list) {
            val hour = record.hours
            hourlyActivity[hour]++
        }

        return Result.success(hourlyActivity)
    }
}