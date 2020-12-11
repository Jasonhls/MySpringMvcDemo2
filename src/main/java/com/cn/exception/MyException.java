package com.cn.exception;

import org.springframework.http.HttpStatus;

/**
 * @description: 自定义异常
 * @author: helisen
 * @create: 2020-12-09 18:14
 **/
public class MyException extends Exception{
    private HttpStatus status;
    private String reason;

    public MyException() {
    }

    public MyException(HttpStatus status) {
        this(null, status, null);
    }

    public MyException(HttpStatus status, String reason) {
        this(null, status, reason);
    }

    public MyException(Throwable cause, HttpStatus status, String reason) {
        super(cause);
        this.status = status;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "MyException{" +
                "status=" + status +
                ", reason='" + reason + '\'' +
                "} " + super.toString();
    }
}
