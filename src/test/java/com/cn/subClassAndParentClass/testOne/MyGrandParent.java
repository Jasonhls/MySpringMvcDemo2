package com.cn.subClassAndParentClass.testOne;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 10:33
 **/
public class MyGrandParent {
    public String getHobby() {
        System.out.println("我是祖父类");
        return "grandParent";
    }

    public void getTest() {
        System.out.println(getHobby());
    }
}
