package com.cn.aop.aopTest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyPlugin implements InvocationHandler {
    private Object target;

    public MyPlugin() {
    }

    public MyPlugin(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行拦截之前");
        if(target != null) {
            return method.invoke(target, args);
        }
        return method.invoke(args);
    }

    public Object plugin(Object target) {
        Class aClass = target.getClass();
        if(aClass.getInterfaces().length > 0) {
            return Proxy.newProxyInstance(aClass.getClassLoader(),aClass.getInterfaces(), new MyPlugin(target));
        }
        return target;
    }


}
