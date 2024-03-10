package com.sleep.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 11:18
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    /**
     * 功能
     */
    String BusinessName();
}

