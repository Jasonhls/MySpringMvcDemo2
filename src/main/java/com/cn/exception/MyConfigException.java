package com.cn.exception;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-11 11:19
 **/
public class MyConfigException extends Exception{
    private Integer code;
    private String message;

    public MyConfigException() {
    }

    public MyConfigException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyConfigException(Throwable cause, Integer code, String message) {
        super(cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
