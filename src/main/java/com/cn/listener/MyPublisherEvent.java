package com.cn.listener;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @Author: helisen
 * @Date 2021/9/10 15:07
 * @Description:
 */
@Component
public class MyPublisherEvent implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void test() {
        MyApplicationEvent myApplicationEvent = new MyApplicationEvent(this);
        applicationEventPublisher.publishEvent(myApplicationEvent);
    }
}