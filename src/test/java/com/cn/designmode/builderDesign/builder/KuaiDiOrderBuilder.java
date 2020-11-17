package com.cn.designmode.builderDesign.builder;

import com.cn.designmode.builderDesign.IBuildOrder;
import com.cn.designmode.builderDesign.order.KuaiDiOrder;
import com.cn.designmode.builderDesign.order.Order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 10:45
 **/
public class KuaiDiOrderBuilder implements IBuildOrder {

    KuaiDiOrder order;

    public KuaiDiOrderBuilder() {
        this.order = new KuaiDiOrder();
    }

    @Override
    public void buildOrderNo() {
        order.setOrderNo("K123456");
    }

    @Override
    public void buildShopId() {
        order.setShopId(6666);
    }

    @Override
    public void buildNum() {
        order.setNum(30);
    }

    @Override
    public void buildTotalFee() {
        order.setTotalFee(2000);
    }

    @Override
    public void buildReceiverName() {
        order.setReceiverName("smith");
    }

    @Override
    public void buildStatus() {
        order.setStatus(3);
    }


    @Override
    public Order createOrder() {
        return order;
    }
}
