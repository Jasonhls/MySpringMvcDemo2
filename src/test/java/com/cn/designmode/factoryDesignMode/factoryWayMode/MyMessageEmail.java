package com.cn.designmode.factoryDesignMode.factoryWayMode;

import static com.cn.designmode.factoryDesignMode.factoryWayMode.MessageConstant.EMAIL;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 13:39
 **/
public class MyMessageEmail extends MyAbstractMessage{
    @Override
    public void sendMessage() throws Exception {
        if(isNotNull(EMAIL)) {
            throw new Exception("发送短信，需要传入" + EMAIL + "参数");
        }
        System.out.println("我是邮箱，发送通知给" + getMessageParam().get(EMAIL));
    }
}
