package com.cn.designmode.builderDesign.order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 09:35
 **/
public class Order {
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 店铺id
     */
    private Integer shopId;
    /**
     * 购买数量
     */
    private Integer num;
    /**
     * 商品总价
     */
    private Integer totalFee;
    /**
     * 收货人姓名
     */
    private String receiverName;
    /**
     * 订单状态
     */
    private Integer status;

    public String getOrderNo() {
        return orderNo;
    }

    public Order setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public Integer getShopId() {
        return shopId;
    }

    public Order setShopId(Integer shopId) {
        this.shopId = shopId;
        return this;
    }

    public Integer getNum() {
        return num;
    }

    public Order setNum(Integer num) {
        this.num = num;
        return this;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public Order setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
        return this;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public Order setReceiverName(String receiverName) {
        this.receiverName = receiverName;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Order setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNo='" + orderNo + '\'' +
                ", shopId=" + shopId +
                ", num=" + num +
                ", totalFee='" + totalFee + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", status=" + status +
                '}';
    }
}
