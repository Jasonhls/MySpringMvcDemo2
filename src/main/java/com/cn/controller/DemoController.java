package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-22 13:35
 **/
@Controller
public class DemoController {
    @RequestMapping(value = "/hello")
    public String sayHello() {
        return "abc";
    }

    @RequestMapping(value = "/testException")
    @ResponseBody
    public String testException() {
        int i = 1 / 0;
        return "abc";
    }
}
