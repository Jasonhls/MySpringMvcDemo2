package com.cn.designmode.builderDesign.order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 10:43
 **/
public class KuaiDiOrder extends Order{
    /**
     * 快递单号
     */
    private String number;

    public String getNumber() {
        return number;
    }

    public KuaiDiOrder setNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public String toString() {
        return "KuaiDiOrder{" +
                "number='" + number + '\'' +
                "} " + super.toString();
    }
}
