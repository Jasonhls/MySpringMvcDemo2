package com.cn.subClassAndParentClass.testProtected.p1;

import com.cn.subClassAndParentClass.testProtected.p2.Son2;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-08 16:14
 **/
public class Test1 {
    public static void main(String[] args) {
        Son1 son1 = new Son1();
        son1.f();

        Son2 son2 = new Son2();
        son2.f();

        Father1 father1 = new Father1();
        father1.f();
    }
}
