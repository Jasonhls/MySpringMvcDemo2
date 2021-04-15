package com.cn.socketAndNetty2.netty.source;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-15 18:17
 **/
public class Test {
    private volatile String name;

    public static void main(String[] args) {
        Test t = new Test();
        t.test("hls");
    }

    public void test(String name) {
        System.out.println(Thread.currentThread().getName() + " " + "=================" + name);
        this.name = "tom";

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + Test.this.getClass().getName());
            System.out.println(Thread.currentThread().getName() + " " + Test.this.name);
        }, "t1").start();

        System.out.println(Thread.currentThread().getName() + " " + "----------" + this.name);
    }
}
