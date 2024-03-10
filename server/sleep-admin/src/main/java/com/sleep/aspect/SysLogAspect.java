package com.sleep.aspect;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.sleep.domain.entity.SysLog;
import com.sleep.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/12/5 12:41
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {
    private SysLogService sysLogService;
    @Autowired
    SysLogAspect(SysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @Pointcut("execution(public * com.sleep.controller.*.*(..))")
    public void mLog4j() {
    }

    @Around("mLog4j()")
    public Object printAndSaveLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object object;
        try {
            beforeProceed(joinPoint); //执行前
            object = joinPoint.proceed(); //执行
            afterProceed(object); //执行后
        } finally {
            // 结束后换行
            log.info("==================================End Admin Controller Log==================================" + System.lineSeparator());
        }
        return object;
    }

    private void beforeProceed(ProceedingJoinPoint joinPoint) {
        log.info("==================================Start Admin Controller Log==================================");
        //记录请求信息
        SysLog sysLog = new SysLog();
        sysLog.setUserType("管理员");

        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 请求 URL
        String url = request.getRequestURL().toString();
        sysLog.setUrl(url);

        // 描述信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            sysLog.setDescription(log.value());
        }

        // Http method
        String requestType = request.getMethod();
        sysLog.setRequestType(requestType);

        // 调用 com.sleep.controller 的全路径以及执行方法
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = signature.getName();
        sysLog.setMethod(methodName);

        // 请求的 IP
        String remoteHost = request.getRemoteHost();
        if(StringUtils.equals(remoteHost, "0:0:0:0:0:0:0:1"))
            sysLog.setIp("127.0.0.1");
        else
            sysLog.setIp(remoteHost);

        // 方法入参
        String param = JSON.toJSONString(joinPoint.getArgs());
        if(param.length() > 100)
            sysLog.setParameter(param.substring(0, 100));
        else
            sysLog.setParameter(param);

        sysLogService.save(sysLog);

        log.info("{}", JSON.toJSONString(sysLog));
    }

    private void afterProceed(Object object) {
        // 打印出参
        log.info("Response       : {}", object);
    }
}
