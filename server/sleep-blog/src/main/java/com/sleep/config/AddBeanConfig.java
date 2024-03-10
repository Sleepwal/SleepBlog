package com.sleep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 添加bean配置
 *
 * @author SleepWalker
 * @date 2023/05/23
 */

@Configuration
public class AddBeanConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
