package com.cn.service;

import com.cn.controller.DemoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-22 13:38
 **/
@Service
public class DemoServiceImpl implements DemoService {
    /**
     *非@controller注解的bean，比如@Service @Component 注册在spring父容器中，而带有@controller注解的bean注册在DispatcherServlet子容器中，
     * 父容器中的bean不能引用子容器中的bean，子容器中的bean可以引用父容器中的bean
     * 所以下面的引用，在启动的时候会报错
     */
    @Autowired
    private DemoController demoController;
    @Override
    public String sayHello(String name) {
        System.out.println(demoController.sayHello());
        return "hello " + name;
    }
}
