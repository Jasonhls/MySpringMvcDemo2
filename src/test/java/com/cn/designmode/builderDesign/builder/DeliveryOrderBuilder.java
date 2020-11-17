package com.cn.designmode.builderDesign.builder;

import com.cn.designmode.builderDesign.IBuildOrder;
import com.cn.designmode.builderDesign.order.DeliveryOrder;
import com.cn.designmode.builderDesign.order.Order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 10:13
 **/
public class DeliveryOrderBuilder implements IBuildOrder {

    Order order;

    public DeliveryOrderBuilder() {
        this.order = new DeliveryOrder();
    }

    @Override
    public void buildOrderNo() {
        order.setOrderNo("D123456");
    }

    @Override
    public void buildShopId() {
        order.setShopId(6869);
    }

    @Override
    public void buildNum() {
        order.setNum(25);
    }

    @Override
    public void buildTotalFee() {
        order.setTotalFee(500);
    }

    @Override
    public void buildReceiverName() {
        order.setReceiverName("tom");
    }

    @Override
    public void buildStatus() {
        order.setStatus(2);
    }

    @Override
    public Order createOrder() {
        return order;
    }
}
