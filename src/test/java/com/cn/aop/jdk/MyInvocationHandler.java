package com.cn.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * 在自定义的InvocationHandler中需要重写3个函数：
 * 1.构造函数，将代理对象传入
 * 2.invoke方法，此方法中实现了AOP增强的所有逻辑
 * 3.getProxy方法，此方法千篇一律，但是必不可少。
 * @author: helisen
 * @create: 2020-12-31 11:19
 **/
public class MyInvocationHandler implements InvocationHandler {
    //目标对象
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("----------before----------");
        //执行目标方法
        Object result = method.invoke(target, args);
        System.out.println("-----------after------------");
        return result;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), target.getClass().getInterfaces(), this);
    }
}
