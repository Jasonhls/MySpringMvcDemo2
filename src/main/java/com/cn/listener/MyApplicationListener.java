package com.cn.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: helisen
 * @Date 2021/9/10 11:27
 * @Description:
 */
@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

    @Override
    public void onApplicationEvent(MyApplicationEvent myApplicationEvent) {
        String test = myApplicationEvent.test();
        System.out.println("监听结果：" + test);
    }
}
