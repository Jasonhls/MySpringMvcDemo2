package com.cn.designmode.factoryDesignMode.factoryWayMode;

import java.util.Map;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 13:33
 **/
public interface IMyMessage {
    Map<String, Object> getMessageParam();
    void setMessageParam(Map<String, Object> messageParam);
    void sendMessage() throws Exception;
}
