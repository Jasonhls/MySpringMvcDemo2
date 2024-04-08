package com.cn.aop.xml;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:46
 **/
public class CglibBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void test() {
        System.out.println("执行cglib业务代码");
    }

}
