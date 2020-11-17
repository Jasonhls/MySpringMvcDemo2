package com.cn.designmode.factoryDesignMode.factoryWayMode;

import java.util.HashMap;
import java.util.Map;

import static com.cn.designmode.factoryDesignMode.factoryWayMode.MessageConstant.*;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 13:35
 **/
public class MyMessageFactory implements MessageFactory{

    @Override
    public IMyMessage createMessage(String messageType) {
        IMyMessage myMessage;
        Map<String, Object> messageParam = new HashMap<>();
        if(SMS.equals(messageType)) {
            myMessage = new MyMessageSms();
            messageParam.put(SMS, "123456789");
        }else if(OA.equals(messageType)) {
            myMessage = new MyMessageOaTodo();
            messageParam.put(OA, "testUser");
        }else if(EMAIL.equals(messageType)) {
            myMessage = new MyMessageEmail();
            messageParam.put(EMAIL, "test@test.com");
        }else {
            myMessage = new MyMessageEmail();
            messageParam.put(EMAIL, "test@test.com");
        }
        myMessage.setMessageParam(messageParam);
        return myMessage;
    }
}
