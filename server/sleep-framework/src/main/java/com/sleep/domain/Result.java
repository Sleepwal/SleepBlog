package com.sleep.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 返回结果类
 *
 * @author SleepWalker
 * @version 1.0
 * @date 2023/5/14 12:21
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result <T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public Result() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
    }
    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }
    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 请求成功（无参）
     *
     * @return {@link Result}<{@link ?}>
     */
    public static Result success() {
        return new Result<>();
    }

    /**
     * 请求成功（data）
     *
     * @param data 数据
     * @return {@link Result}<{@link ?}>
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        if(data != null)
            result.setData(data);
        return result;
    }

    /**
     * 请求失败（code, msg）
     *
     * @param code 代码
     * @param msg  信息
     * @return {@link Result}<{@link ?}>
     */
    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg);
    }

    /**
     * 请求失败（enums）
     *
     * @param enums 枚举
     * @return {@link Result}<{@link ?}>
     */
    public static Result fail(ResultEnum enums){
        return new Result<>(enums.getCode(), enums.getMsg());
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }
}
