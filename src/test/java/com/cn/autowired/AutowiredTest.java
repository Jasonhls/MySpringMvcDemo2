package com.cn.autowired;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-26 09:13
 **/
public class AutowiredTest {
    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        TeacherDemo bean = context.getBean(TeacherDemo.class);
        System.out.println(bean.toString());
    }
}