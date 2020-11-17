package com.cn.designmode.factoryDesignMode.factoryWayMode;

import static com.cn.designmode.factoryDesignMode.factoryWayMode.MessageConstant.OA;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 13:41
 **/
public class MyMessageOaTodo extends MyAbstractMessage{
    @Override
    public void sendMessage() throws Exception {
        if(isNotNull(OA)) {
            throw new Exception("发送OA待办，需要传入" + OA + "参数");
        }
        System.out.println("我是OA待办，发送通知给" + getMessageParam().get(OA));
    }
}
