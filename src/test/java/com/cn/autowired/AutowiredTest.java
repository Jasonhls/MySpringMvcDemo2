package com.cn.autowired;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-26 09:13
 **/
public class AutowiredTest {
    @Test
    public void testAutowiredByConstructor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        AutowiredByConstructor bean = context.getBean(AutowiredByConstructor.class);
        System.out.println(bean.toString());
    }

    @Test
    public void testAutowiredByField() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        AutowiredByField bean = context.getBean(AutowiredByField.class);
        System.out.println(bean.toString());
    }

    @Test
    public void testAutowiredBySetter() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        AutowiredBySetter bean = context.getBean(AutowiredBySetter.class);
        System.out.println(bean.toString());
    }

    @Test
    public void testAutowiredBySetterTwo() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        AutowiredBySetterTwo bean = context.getBean(AutowiredBySetterTwo.class);
        System.out.println(bean.toString());
    }

    @Before
    public void beforeWay() {
        System.out.println("测试开始之前：");
    }

    @After
    public void afterWay() {
        System.out.println("测试结束");
    }

    @Test
    public void testDemo() {
        System.out.println("我是测试方法。。。");
    }
}