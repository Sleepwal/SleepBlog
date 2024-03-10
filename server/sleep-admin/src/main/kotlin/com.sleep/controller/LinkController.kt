package com.sleep.controller

import com.sleep.domain.Result
import com.sleep.domain.dto.admin.AddOrUpdateLinkDto
import com.sleep.service.LinkService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/9 20:01
 */
@RestController
@RequestMapping("/admin/link")
open class LinkController {
    @Autowired
    private val linkService: LinkService? = null

    @GetMapping("/linkListByPage")
    open fun getLinkListByPage(pageNum:Int, size:Int):Result<*> = linkService!!.getLinkListByPage(pageNum, size)

    @PostMapping("/addOrUpdateLink")
    open fun addOrUpdateLink(@RequestBody @Validated dto: AddOrUpdateLinkDto):Result<*> = linkService!!.addOrUpdateLink(dto)

    @DeleteMapping("/deleteLink/{id}")
    open fun deleteLink(@PathVariable id:Long):Result<*>  = linkService!!.deleteLink(id)
}
