package com.cn.designmode.builderDesign;

import com.cn.designmode.builderDesign.order.Order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 09:38
 **/
public class Director {
    public Order createHumanByDirector(IBuildOrder bh) {
        bh.buildOrderNo();
        bh.buildShopId();
        bh.buildNum();
        bh.buildTotalFee();
        bh.buildReceiverName();
        bh.buildStatus();
        return bh.createOrder();
    }
}
