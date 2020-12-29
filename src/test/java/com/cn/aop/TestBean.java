package com.cn.aop;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:46
 **/
public class TestBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void test() {
        System.out.println("test");
    }

}
