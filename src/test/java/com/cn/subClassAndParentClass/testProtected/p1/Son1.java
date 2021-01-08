package com.cn.subClassAndParentClass.testProtected.p1;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-08 16:14
 **/
public class Son1 extends Father1{
    private void testSon1() {
        //Son1与父类在同一个包，可以访问从父类继承而来的protected方法
        f();
    }

    private void testSon1Two() {
        Father1 father1 = new Father1();
        //Son1与父类在同一个包，可以访问父类实例的protected方法
        father1.f();
    }
}
