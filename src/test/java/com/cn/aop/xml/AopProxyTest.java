package com.cn.aop.xml;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:59
 **/
public class AopProxyTest {


    /**
     * 测试Spring中CGLIB动态代理
     */
    @Test
    public void testCGLIB() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/aop/cglib.xml");
        //下面方法中的入参即bean的名称，即xml中定义的bean标签的id的值。
        CglibBean cglibBean = (CglibBean) context.getBean("cglibBean");
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
}
