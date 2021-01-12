package com.cn.asm.two.proxy;

import com.cn.asm.two.SecurityChecker;
import com.cn.asm.two.Account;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-12 09:21
 **/
public class SecurityProxyInvocationHandler implements InvocationHandler {

    /**
     * 需要被代理的对象
     */
    private Object proxy;

    public SecurityProxyInvocationHandler(Object o) {
        this.proxy = o;
    }

    /**
     * @param object  生成的代理对象
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object object, Method method, Object[] args) throws Throwable {
        if(object instanceof Account && method.getName().equals("operation")) {
            SecurityChecker.checkSecurity();
        }
        return method.invoke(proxy, args);
    }
}
