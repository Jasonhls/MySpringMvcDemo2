package com.cn.designmode.factoryDesignMode.factoryWayMode;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 13:32
 **/
public interface MessageFactory {
    IMyMessage createMessage(String messageType);
}
