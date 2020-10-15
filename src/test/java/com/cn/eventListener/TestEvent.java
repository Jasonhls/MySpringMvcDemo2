package com.cn.eventListener;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-14 14:34
 **/
public class TestEvent extends ApplicationEvent {

    /**
     * 这里必须得写一个构造函数，因为父类ApplicationEvent没有无参构造，因此继承了ApplicationEvent，就没有构造函数了
     * @param source
     */
    public TestEvent(Object source) {
        super(source);
    }

    private String msg;

    public TestEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public void print() {
        System.out.println(msg);
    }
}
