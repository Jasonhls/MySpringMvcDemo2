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
public class EnhancerDemo {
    /**
     * 执行结果如下：
     * before invoke public void com.cn.aop.cglib.EnhancerDemo.test()
     * EnhancerDemo test()
     * after invoke public void com.cn.aop.cglib.EnhancerDemo.test()
     * before invoke public java.lang.String java.lang.Object.toString()
     * before invoke public native int java.lang.Object.hashCode()
     * after invoke public native int java.lang.Object.hashCode()
     * after invoke public java.lang.String java.lang.Object.toString()
     * com.cn.aop.cglib.EnhancerDemo$$EnhancerByCGLIB$$bd0b97b5@3108bc
     * @param args
     */
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(EnhancerDemo.class);
        enhancer.setCallback(new MethodInterceptorImpl());

        EnhancerDemo demo = (EnhancerDemo)enhancer.create();
        demo.test();;
        System.out.println(demo);
    }

    public void test() {
        System.out.println("EnhancerDemo test()");
    }

    private static class MethodInterceptorImpl implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("before invoke " + method);
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("after invoke " + method);
            return result;
        }
    }
}
