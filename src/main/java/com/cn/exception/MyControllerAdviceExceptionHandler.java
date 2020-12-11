package com.cn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-10 11:29
 **/
@ControllerAdvice
public class MyControllerAdviceExceptionHandler {

    /**
     * 处理MyConfigException类型的异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MyConfigException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseData customHandlerException(MyConfigException ex) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(ex.getCode());
        responseData.setMessage(ex.getMessage());
        return responseData;
    }
}
