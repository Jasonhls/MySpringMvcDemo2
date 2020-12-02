package com.cn.subClassAndParentClass.testOne;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 10:34
 **/
public class MyParent extends MyGrandParent{
    @Override
    public String getHobby() {
        System.out.println(super.getHobby());
        System.out.println("我是父亲类");
        return "myParent";
    }
}
