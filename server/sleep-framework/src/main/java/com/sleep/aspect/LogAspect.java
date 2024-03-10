package com.sleep.aspect;

import com.alibaba.fastjson.JSON;
import com.sleep.annotation.MyLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 11:19
 */

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.sleep.annotation.MyLog)")
    public void pointCut() {}

    @Around("pointCut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object;
        try {
            beforeProceed(joinPoint);
            object = joinPoint.proceed();
            afterProceed(object);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return object;
    }

    private void beforeProceed(ProceedingJoinPoint joinPoint) {
        log.info("=======Start=======");

        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURL());

        // 打印描述信息
        log.info("BusinessName   : {}", getBusinessName(joinPoint));
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 com.sleep.controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印方法入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private Object getBusinessName(ProceedingJoinPoint joinPoint) {
        //获取切点的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //反射获取方法上的注解MyLog
        MyLog myLog = signature.getMethod().getAnnotation(MyLog.class);
        return myLog.BusinessName();
    }

    private void afterProceed(Object object) {
        // 打印出参
        log.info("Response       : {}", object);
    }
}
