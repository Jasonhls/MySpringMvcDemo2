package com.cn.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: helisen
 * @Date 2021/9/10 11:29
 * @Description:
 */
public class MyApplicationEvent extends ApplicationEvent {

    public MyApplicationEvent(Object source) {
        super(source);
    }

    public String test() {
        System.out.println("你好");
        return "l am the event";
    }
}