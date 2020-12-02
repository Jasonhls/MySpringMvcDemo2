package com.cn.subClassAndParentClass.testTwo;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 15:42
 **/
public class A<T> {

    private T obj;

    public String sayHello() {
        return "hello";
    }

    protected String aProtectedWay() {
        return "a protected";
    }

    private String aPrivateWay() {
        return "a private";
    }

    public void aPublicWay() {

    }

    public T getA() {
        return obj;
    }
}
