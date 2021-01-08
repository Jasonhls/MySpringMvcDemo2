package com.cn.subClassAndParentClass.testProtected.p2;

import com.cn.subClassAndParentClass.testProtected.p1.Father1;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-08 16:14
 **/
public class Son2 extends Father1 {
    private void testSon2() {
        //Son2与父类不在同一个包，可以反问从父类继承而来的protected方法
        f();  //编译通过
    }

    private void testSon2Two() {
        Father1 father1 = new Father1();
        //Son2与父类不在同一个包，不能返回父类实例的protected方法
//        father1.f();  //编译报错
    }
}
