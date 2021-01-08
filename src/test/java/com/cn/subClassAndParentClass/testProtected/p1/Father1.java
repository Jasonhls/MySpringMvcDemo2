package com.cn.subClassAndParentClass.testProtected.p1;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-08 16:13
 **/
public class Father1 {
    protected void f(){}

    private void test() {
        //本类的protected方法随便调用
        f();
    }
}
