package com.sleep.handle.exception;

import com.sleep.domain.Result;
import com.sleep.domain.ResultEnum;
import com.sleep.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;


/**
 * @author manson
 * @date 2023/05/14
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 系统异常处理
     *
     * @param e e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(SystemException.class)
    public Result<?> systemExceptionHandle(SystemException e) {
        //打印异常信息
        log.error("出现了{}! msg:{}", e, e.getMessage());
        //从异常对象中获取提示信息封装返回
        return Result.fail(e.getCode(), e.getMsg());
    }

    /**
     * 处理验证异常
     *
     * @param e e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public Result<?> handleValidatedException(Exception e) {
        String msg = null;

        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            msg = ex.getBindingResult().getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining("; "));
        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException ex = (ConstraintViolationException) e;
            msg = ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "));
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            msg = ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage).toString();
        }

        return Result.fail(400, msg);
    }

    /**
     * 异常处理
     *
     * @param e e
     * @return {@link Result}<{@link ?}>
     */
    @ExceptionHandler(Exception.class)
    public Result<?> ExceptionHandle(Exception e) {
        //打印异常信息
        log.error("出现了{}! msg:{}", e, e.getMessage());
        //从异常对象中获取提示信息封装返回
        return Result.fail(ResultEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
