package com.cn.designmode.builderDesign.order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 10:15
 **/
public class DeliveryOrder extends Order{
    /**
     *配送开始时间
     */
    private String deliveryStartTime;

    /**
     * 配送结束时间
     */
    private String deliveryEndTime;

    public String getDeliveryStartTime() {
        return deliveryStartTime;
    }

    public DeliveryOrder setDeliveryStartTime(String deliveryStartTime) {
        this.deliveryStartTime = deliveryStartTime;
        return this;
    }

    public String getDeliveryEndTime() {
        return deliveryEndTime;
    }

    public DeliveryOrder setDeliveryEndTime(String deliveryEndTime) {
        this.deliveryEndTime = deliveryEndTime;
        return this;
    }

    @Override
    public String toString() {
        return "DeliveryOrder{" +
                "deliveryStartTime='" + deliveryStartTime + '\'' +
                ", deliveryEndTime='" + deliveryEndTime + '\'' +
                "} " + super.toString();
    }
}
