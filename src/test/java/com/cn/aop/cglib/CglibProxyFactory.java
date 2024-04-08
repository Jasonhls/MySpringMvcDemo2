package com.cn.aop.cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description: Enhancer方式的AOP的用法
 * CDLIB中对于方法的拦截是通过将自定义的拦截器（实现MethodInterceptor）接口加入Callback中并在调用代理时直接激活
 * 拦截器中的interceptor方法实现的。
 * @author: helisen
 * @create: 2020-12-31 14:01
 **/
public class CglibProxyFactory implements MethodInterceptor{

    private Object target;

    public CglibProxyFactory(Object target) {
        this.target = target;
    }

    //获取代理对象的方法
    public Object getProxyObject() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before invoke " + method);
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("after invoke " + method);
        return result;
    }
}
