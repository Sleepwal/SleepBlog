package com.sleep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/17 18:21
 */

@Configuration
public class AddBeanConfig {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
