package com.cn.eventListener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-14 14:45
 **/
public class EventListenerTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/eventListener/applicationContext.xml");
        TestEvent event = new TestEvent("hello", "msg");
        context.publishEvent(event);
    }
}
