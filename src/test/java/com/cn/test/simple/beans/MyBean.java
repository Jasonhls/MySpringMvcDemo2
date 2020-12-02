package com.cn.test.simple.beans;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-25 18:16
 **/
public class MyBean {

    @Autowired(required = false)
    private TestBean testBean2;

    @Autowired
    private ITestBean testBean3;

    private ITestBean testBean4;


    @Autowired
    public void inject(ITestBean testBean4) {
        this.testBean4 = testBean4;
    }

    public TestBean getTestBean2() {
        return testBean2;
    }

    public ITestBean getTestBean3() {
        return testBean3;
    }

    public ITestBean getTestBean4() {
        return testBean4;
    }
}
