package com.cn.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:59
 **/
public class TestDemo {
    /**
     * 测试Spring中CGLIB动态代理
     */
    @Test
    public void testCGLIB() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/aop/cglib.xml");
        CglibBean cglibBean = (CglibBean) context.getBean("test");
        cglibBean.test();
    }

    /**
     * 测试Spring中JDK动态代理
     */
    @Test
    public void testJDK() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/aop/jdk.xml");
        JdkService bean = context.getBean(JdkService.class);
        bean.test("hls");
    }

    @Test
    public void test2() {
        Class<CglibBean> beanClass = CglibBean.class;
        String beanName = beanClass.getName();
        if (!StringUtils.hasLength(beanName) || beanName.length() !=
                beanClass.getName().length() + "&".length()) {
            System.out.println(false);
        }
         boolean b = (beanName.startsWith(beanClass.getName()) &&
                beanName.endsWith("&"));
        System.out.println(b);
    }

    @Test
    public void test3() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        list.add(0, 9);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }
}
