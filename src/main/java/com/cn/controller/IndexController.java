package com.cn.controller;

import com.cn.pojo.Student;
import com.cn.service.DemoService;
import com.cn.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-20 10:21
 **/
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/abc")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView model=new ModelAndView("abc");
        model.addObject("message","这是第一个SpringMVC网页");
        return model;
    }

    @RequestMapping(value = "/efg")
    public String getEfg(String name) {
        System.out.println(indexService.sayHello(name));
        return "efg";
    }


    @RequestMapping(value = "/test")
    public String testServiceUseController(String name) {
        System.out.println(demoService.sayHello(name));
        return "efg";
    }

    @RequestMapping(value = "/returnJson")
    @ResponseBody
    public Student returnJson() {
        Student s = new Student();
        s.setName("hls");
        s.setAge(28);
        return s;
    }


}
