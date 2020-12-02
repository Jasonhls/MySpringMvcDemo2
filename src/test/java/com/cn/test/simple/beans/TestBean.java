package com.cn.test.simple.beans;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-25 18:01
 **/
public class TestBean implements ITestBean{
    private String name;

    @Override
    public String getName() {
        return name;
    }
}
