package com.cn.aop;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-08 16:46
 **/
public class AopMethodDemo {
    public String sayHello(String name) {
        return "say hello " + name;
    }

    public String eat() {
        return "eat dinner";
    }
}
