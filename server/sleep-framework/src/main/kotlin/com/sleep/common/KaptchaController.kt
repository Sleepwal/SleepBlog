package com.sleep.common

import com.google.code.kaptcha.Constants
import com.google.code.kaptcha.impl.DefaultKaptcha
import com.sleep.constants.SystemConstants
import com.sleep.utils.RedisCache
import com.sleep.utils.VerifyCodeUtils
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletResponse

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/24 20:24
 */
@RequestMapping(value = ["/common"])
@RestController
open class KaptchaController {
    @Autowired
    private val redisCache: RedisCache? = null

    @Autowired
    private val defaultKaptcha: DefaultKaptcha? = null

    @ApiOperation(value = "生成验证码图片")
    @GetMapping(value = ["/captcha"])
    @Throws(IOException::class)
    open fun createImageCode(response: HttpServletResponse) = doCreateCode(response, "user")


    @GetMapping("/admin/captcha")
    @Throws(IOException::class)
    open fun adminCreateImageCode(response: HttpServletResponse) = doCreateCode(response, "admin")


    @Throws(IOException::class)
    private fun doCreateCode(response: HttpServletResponse, type: String) {
        //禁止缓存
        response.setDateHeader("Expires", 0)
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate")
        response.addHeader("Cache-Control", "post-check=0, pre-check=0")
        response.setHeader("Pragma", "no-cache")
        //设置响应格式为png图片
        response.contentType = "image/png"

        // 生成图片验证码
        val image = BufferedImage(250, 75, BufferedImage.TYPE_INT_RGB)
        val randomText = VerifyCodeUtils.drawRandomText(image)

        //存到redis中
        when (type) {
            "admin" -> redisCache!!.setCacheObject(
                SystemConstants.ADMIN_LOGIN_CAPTCHA_KEY,
                randomText,
                60,
                TimeUnit.SECONDS
            )

            else -> redisCache!!.setCacheObject(Constants.KAPTCHA_SESSION_KEY, randomText, 60, TimeUnit.SECONDS)
        }

        val out = response.outputStream
        ImageIO.write(image, "png", out)
        out.flush()
        out.close()
    }
}
