package com.cn.controller;

import com.cn.exception.MyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-10 09:17
 **/
@RestController
@RequestMapping("/exception")
public class ExceptionController {
    @RequestMapping(value = "/test")
    @ResponseBody
    public ModelAndView testException(int m) throws MyException {
        if(m > 0) {
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR, "发生异常了");
        }
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "hls");
        mav.addObject("age", "29");
        mav.setViewName("hls");
        return mav;
    }
}
