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
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseData customHandlerException(Exception e) {
        ResponseData responseData = new ResponseData();
        responseData.setCode(500);
        responseData.setMessage(e.getMessage());
        return responseData;
    }
}
