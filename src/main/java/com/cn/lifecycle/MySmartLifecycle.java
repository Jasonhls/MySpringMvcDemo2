package com.cn.lifecycle;

import org.springframework.context.SmartLifecycle;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-15 11:17
 **/
public class MySmartLifecycle implements SmartLifecycle {
    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable runnable) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
