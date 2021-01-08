package com.cn.subClassAndParentClass.testProtected.p2;

import com.cn.subClassAndParentClass.testProtected.p1.Father1;
import com.cn.subClassAndParentClass.testProtected.p1.Son1;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-08 16:17
 **/
public class Test2 {
    public static void main(String[] args) {
        Father1 father1 = new Father1();
        //不同包，当前类也不是子类，不能使用protected方法
//        father1.f(); 编译报错

        Son2 son2 = new Son2();
        //不同包，当前类也不是子类，不能使用protected方法
//        son2.f();  编译报错

        Son1 son1 = new Son1();
        //不同包，当前类也不是子类，不能使用protected方法
//        son1.f(); 编译报错
    }
}
