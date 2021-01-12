package com.cn.aop;

import com.cn.subClassAndParentClass.testTwo.A;
import org.junit.Test;
import org.springframework.cglib.core.AbstractClassGenerator;
import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.cglib.core.KeyFactory;
import org.springframework.cglib.core.ReflectUtils;
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

    public static final DefaultGeneratorStrategy INSTANCE = new DefaultGeneratorStrategy();

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

    @Test
    public void test4() throws Exception {

//        DefaultGeneratorStrategy instance = DefaultGeneratorStrategy.INSTANCE;
//        KeyFactory.Generator generator = new KeyFactory.Generator();
//        byte[] b = instance.generate(generator);
//
////        AopMethodDemo amd = (AopMethodDemo)ReflectUtils.newInstance(AopMethodDemo.class);
//        Class hls = ReflectUtils.defineClass("hls", b, this.getClassLoader());
//        Object o = ReflectUtils.newInstance(hls);
//        AopMethodDemo amd = (AopMethodDemo)o;
//        System.out.println(amd.eat());
    }

    public ClassLoader getClassLoader() {
        ClassLoader t = null;


        if (t == null) {
            t = this.getClass().getClassLoader();
        }

        if (t == null) {
            t = Thread.currentThread().getContextClassLoader();
        }

        if (t == null) {
            throw new IllegalStateException("Cannot determine classloader");
        } else {
            return t;
        }
    }
}
