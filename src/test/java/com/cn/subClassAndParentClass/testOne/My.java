package com.cn.subClassAndParentClass.testOne;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 10:35
 **/
public class My extends MyParent{
    @Override
    public String getHobby() {
        System.out.println(super.getHobby());
        System.out.println("我本人");
        return "me";
    }
}
