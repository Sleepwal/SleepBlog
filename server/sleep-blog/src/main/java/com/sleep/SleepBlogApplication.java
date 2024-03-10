package com.sleep;

import com.sleep.im.IMServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 11:21
 */


@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SleepBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(SleepBlogApplication.class);
        IMServer.start();
    }
}
