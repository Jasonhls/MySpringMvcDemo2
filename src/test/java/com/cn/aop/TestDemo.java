package com.cn.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:59
 **/
public class TestDemo {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/aop/aspectTest.xml");
        TestBean testBean= (TestBean) context.getBean("test");
        testBean.test();
    }

    @Test
    public void test2() {
        Class<TestBean> beanClass = TestBean.class;
        String beanName = beanClass.getName();
        if (!StringUtils.hasLength(beanName) || beanName.length() !=
                beanClass.getName().length() + "&".length()) {
            System.out.println(false);
        }
         boolean b = (beanName.startsWith(beanClass.getName()) &&
                beanName.endsWith("&"));
        System.out.println(b);
    }
}
