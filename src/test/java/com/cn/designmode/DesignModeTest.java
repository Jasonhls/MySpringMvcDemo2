package com.cn.designmode;

import com.cn.designmode.builderDesign.Director;
import com.cn.designmode.builderDesign.builder.DeliveryOrderBuilder;
import com.cn.designmode.builderDesign.builder.KuaiDiOrderBuilder;
import com.cn.designmode.builderDesign.order.DeliveryOrder;
import com.cn.designmode.builderDesign.order.Order;
import com.cn.designmode.builderDesign.builder.SelfFetchOrderBuilder;
import com.cn.designmode.factoryDesignMode.abstractFactoryMode.Factory;
import com.cn.designmode.factoryDesignMode.abstractFactoryMode.IFactory;
import com.cn.designmode.factoryDesignMode.factoryWayMode.IMyMessage;
import com.cn.designmode.factoryDesignMode.factoryWayMode.MessageFactory;
import com.cn.designmode.factoryDesignMode.factoryWayMode.MyMessageFactory;
import org.junit.Test;

import static com.cn.designmode.factoryDesignMode.factoryWayMode.MessageConstant.*;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 09:27
 **/
public class DesignModeTest {
    @Test
    public void testBuilderModeOne() {
        Director director = new Director();
        Order hm = director.createHumanByDirector(new SelfFetchOrderBuilder());
        System.out.println(hm);
    }

    @Test
    public void testBuilderModeTwo() {
        Director director = new Director();
        Order hm = director.createHumanByDirector(new DeliveryOrderBuilder());
        DeliveryOrder deliveryOrder =(DeliveryOrder) hm;
        deliveryOrder = deliveryOrder.setDeliveryStartTime("2020-09-13 00:00:00");
        System.out.println(deliveryOrder);
    }

    @Test
    public void testBuilderModeThree() {
        Director director = new Director();
        Order hm = director.createHumanByDirector(new KuaiDiOrderBuilder());
        System.out.println(hm);
    }

    @Test
    public void testFactoryMode() {
        MessageFactory messageFactory = new MyMessageFactory();
        IMyMessage myMessage;
        try {
            myMessage = messageFactory.createMessage(SMS);
            myMessage.sendMessage();

            myMessage = messageFactory.createMessage(OA);
            myMessage.sendMessage();

            myMessage = messageFactory.createMessage(EMAIL);
            myMessage.sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAbstractFactory() {
        IFactory factory = new Factory();
        factory.createProduct1().show();
        factory.createProduct2().show();
    }
}
