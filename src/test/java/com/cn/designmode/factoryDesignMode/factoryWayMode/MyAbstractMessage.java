package com.cn.designmode.factoryDesignMode.factoryWayMode;

import java.util.Map;

/**
 * @description: 抽象类继承接口的时候，可以不实现父类的方法，也可以只实现一些方法
 * @author: helisen
 * @create: 2020-11-16 13:37
 **/
public abstract class MyAbstractMessage implements IMyMessage{

    private Map<String, Object> messageParam;

    @Override
    public Map<String, Object> getMessageParam() {
        return messageParam;
    }

    @Override
    public void setMessageParam(Map<String, Object> messageParam) {
        this.messageParam = messageParam;
    }

    public Boolean isNotNull(String type) {
        return getMessageParam() == null || getMessageParam().get(type) == null
                || "".equals(getMessageParam().get(type));
    }
}
