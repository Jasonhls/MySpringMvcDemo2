package com.cn.java8;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 15:04
 **/
public class Student {
    public String doSomething(final String name , Person person) {
        return name + person.eat("apple");
    }
}
