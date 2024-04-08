package com.cn.aop.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @description:
 * 在自定义的InvocationHandler中需要重写3个函数：
 * 1.构造函数，将代理对象传入
 * 2.invoke方法，此方法中实现了AOP增强的所有逻辑
 * 3.getProxy方法，此方法千篇一律，但是必不可少。
 * @author: helisen
 * @create: 2020-12-31 11:19
 **/
public class JdkProxyFactory implements InvocationHandler {
    //目标对象
    private Object target;

    public JdkProxyFactory(Object target) {
        this.target = target;
    }

    //proxy是代理对象
    //method是被代理的方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用目标对象原来的方法
        //参数1：目标对象
        //参数2：目标对象方法的参数
        Object result = method.invoke(target, args);
        if(Objects.equals(method.getName(), "add")) {
            System.out.println("专门针对add方法进行的后置处理逻辑！");
        }
        //返回执行结果
        return result;
    }

    public Object getProxy() {
        //参数1：目标对象的类加载器
        //参数2：目标对象的所有实现接口列表
        //参数3：回调方法
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
}
