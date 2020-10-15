package com.cn.eventListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-14 14:41
 **/
public class TestListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof TestEvent) {
            TestEvent testEvent = (TestEvent)applicationEvent;
            testEvent.print();
        }
    }
}
