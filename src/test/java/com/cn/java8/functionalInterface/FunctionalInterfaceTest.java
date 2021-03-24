package com.cn.java8.functionalInterface;

import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-24 18:07
 **/
public class FunctionalInterfaceTest {
    @Test
    public void test() {
        GreetingService greetingService = (message) -> {
            System.out.println("say " + message + " to hls");
        };
        greetingService.sayMessage("hello");
    }
}
