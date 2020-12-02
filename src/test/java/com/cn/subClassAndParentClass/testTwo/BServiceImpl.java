package com.cn.subClassAndParentClass.testTwo;

import com.cn.test.simple.beans.TestBean;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 15:43
 **/
public class BServiceImpl extends A<TestBean> implements IBService {

    private String name;

    @Override
    public String eat(String name) {
        return name + " eat food";
    }

    @Override
    public String sayHello() {
        return "hello , 大家好";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String privateWay() {
        return "private";
    }

    protected String protectedWay() {
        return "protected";
    }
}
