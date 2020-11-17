package com.cn.designmode.builderDesign.builder;

import com.cn.designmode.builderDesign.IBuildOrder;
import com.cn.designmode.builderDesign.order.Order;
import com.cn.designmode.builderDesign.order.SelfFetchOrder;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 09:36
 **/
public class SelfFetchOrderBuilder implements IBuildOrder {

    Order order;

    public SelfFetchOrderBuilder() {
        this.order = new SelfFetchOrder();
    }

    @Override
    public void buildOrderNo() {
        order.setOrderNo("ZT123456");
    }

    @Override
    public void buildShopId() {
        order.setShopId(6398);
    }

    @Override
    public void buildNum() {
        order.setNum(100);
    }

    @Override
    public void buildTotalFee() {
        order.setTotalFee(1000);
    }

    @Override
    public void buildReceiverName() {
        order.setReceiverName("hls");
    }

    @Override
    public void buildStatus() {
        order.setStatus(0);
    }

    @Override
    public Order createOrder() {
        return order;
    }
}
