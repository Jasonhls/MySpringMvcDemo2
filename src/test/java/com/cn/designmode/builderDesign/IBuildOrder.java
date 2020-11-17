package com.cn.designmode.builderDesign;

import com.cn.designmode.builderDesign.order.Order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 09:34
 **/
public interface IBuildOrder {
    void buildOrderNo();
    void buildShopId();
    void buildNum();
    void buildTotalFee();
    void buildReceiverName();
    void buildStatus();
    Order createOrder();
}
