package com.cn.java8.functionalInterface;

/**
 * @description: @FunctionalInterface注解只是在编译期间起到一个检查的作用。
 * 仅对抽象方法进行检查
 * @author: helisen
 * @create: 2021-03-24 18:05
 **/
@FunctionalInterface
public interface GreetingService {
    void sayMessage(String message);

    // java.lang.Object中的方法不是抽象方法
    public boolean equals(Object var1);

    // default不是抽象方法
    public default void defaultMethod() {
        System.out.println("Hello ArvinWoo");
    }

    //static不是抽象方法
    public static void staticMethod() {

    }
}
