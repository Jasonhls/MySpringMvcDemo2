package com.cn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
