package com.sleep.constants;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/6/24 20:32
 */

public class SystemConstants {
    /**
     * 存入redis登录验证码的key
     */
    public static final String ADMIN_LOGIN_CAPTCHA_KEY = "admin:login:captcha";

    /**
     * 存入redis浏览量的key
     */
    public static final String VIEW_COUNT_KEY = "article:viewCount";

    /**
     * 存入redis 用户访问量 的key
     */
    public static final String USER_ACCESS_COUNT = "article:accessCount";
}
