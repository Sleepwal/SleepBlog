package com.sleep.config.kaptcha;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置类
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 验证码是否带边框 No
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 验证码字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        // 验证码整体宽度
        properties.setProperty("kaptcha.image.width", "300");
        // 验证码整体高度
        properties.setProperty("kaptcha.image.height", "75");
        // 文字个数
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        // 文字大小
        properties.setProperty("kaptcha.textproducer.font.size","50");
        // 文字随机字体
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        // 文字距离
        properties.setProperty("kaptcha.textproducer.char.space","16");
        // 干扰线颜色
        properties.setProperty("kaptcha.noise.color","blue");
        // 自定义验证码背景
//        properties.setProperty("kaptcha.background.impl","com.sleep.config.kaptcha.NoKaptchaBackhround");

        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}