package com.cn.aop.jdk;

import org.junit.Test;

/**
 * @description: JDK方式的AOP的用法
 * 构造函数，将代理的对象传进去
 * invoke方法，此方法中实现了AOP增强的所有逻辑
 * getProxy方法，此方法千篇一律。
 * @author: helisen
 * @create: 2020-12-31 11:22
 **/
public class JDKProxyTest {

    public static void main(String[] args) {
        //生成$Proxy0的class文件
        //JDK动态代理生成的文件默认在sun/proxy下，如果没有该目录会报Exception in thread “main” java.lang.InternalError:
        // I/O exception saving generated file: java.io.FileNotFoundException: sun\proxy$Proxy0.class (系统找不到指定的路径。)
        //注意：这里必须使用main方法，不能使用@Test，因为使用@Test，下面代码会后执行，导致在sun.misc.ProxyGenerator.generateProxyClass方法判断的时候拿到的还是false。
        //生成的class文件为class $Proxy0 extends Proxy implements UserService，在项目的com/sun/proxy目录下，
        //可以看到add方法中调用了invoke方法，实现代理效果。
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        //实例化目标方法
        UserService userService = new UserServiceImpl();
        //实例化InvocationHandler
        JdkProxyFactory invocationHandler = new JdkProxyFactory(userService);
        //根据目标对象生成代理对象
        UserService proxy = (UserService) invocationHandler.getProxy();
        //调用代理对象的方法
        proxy.add("张三");
    }

    /*@Test
    public void testJDKProxy() {

    }*/
}