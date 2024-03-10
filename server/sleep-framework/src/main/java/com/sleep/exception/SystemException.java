package com.sleep.exception;

import com.sleep.domain.ResultEnum;


/**
 * 系统异常
 *
 * @author manson
 * @date 2023/05/14
 */
public class SystemException extends RuntimeException {
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }
}
