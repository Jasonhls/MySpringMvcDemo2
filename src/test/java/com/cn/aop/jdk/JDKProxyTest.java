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
    @Test
    public void testJDKProxy() {
        //实例化目标方法
        UserService userService = new UserServiceImpl();
        //实例化InvocationHandler
        MyInvocationHandler invocationHandler = new MyInvocationHandler(userService);
        //根据目标对象生成代理对象
        UserService proxy = (UserService) invocationHandler.getProxy();
        //调用代理对象的方法
        proxy.add();
    }
}