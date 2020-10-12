package com.cn.controller;

import com.cn.service.DemoService;
import com.cn.service.DemoServiceImpl;
import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-12 18:04
 **/
public class TestController {
    @Test
    public void testIsAssignableFrom() {
        //父类在前面，子类在后面，会返回true，无论是继承还是实现，都可返回true
        boolean result = Exception.class.isAssignableFrom(ArithmeticException.class);
        boolean resultTwo = ArithmeticException.class.isAssignableFrom(Exception.class);
        //true
        System.out.println(result);
        //false
        System.out.println(resultTwo);

        boolean resultThree = DemoService.class.isAssignableFrom(DemoServiceImpl.class);
        boolean resultFour = DemoServiceImpl.class.isAssignableFrom(DemoService.class);
        //true
        System.out.println(resultThree);
        //false
        System.out.println(resultFour);
    }
}
