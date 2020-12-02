package com.cn.java8;

import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 15:40
 **/
public class Java8Test {
    @Test
    public void test() {
        Student s = new Student();
        String result = s.doSomething("hls", food -> {
            System.out.println("开始执行方法体了");
            System.out.println("参数为：" + food);
            return " eat " + food;
        });
        System.out.println(result);
    }
}
