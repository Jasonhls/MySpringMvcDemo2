package com.cn.designmode.factoryDesignMode.factoryWayMode;

import static com.cn.designmode.factoryDesignMode.factoryWayMode.MessageConstant.SMS;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 13:48
 **/
public class MyMessageSms extends MyAbstractMessage{
    @Override
    public void sendMessage() throws Exception {
        if(isNotNull(SMS)) {
            throw new Exception("发送短信，需要传入"+SMS+ "参数");
        }
        System.out.println("我是短信，发送通知给" + getMessageParam().get(SMS));
    }
}
