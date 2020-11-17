package com.cn.designmode.builderDesign.order;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 10:14
 **/
public class SelfFetchOrder extends Order{
    /**
     * 自提码
     */
    private String verifyCode;

    public String getVerifyCode() {
        return verifyCode;
    }

    public SelfFetchOrder setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
        return this;
    }

    @Override
    public String toString() {
        return "SelfFetchOrder{" +
                "verifyCode='" + verifyCode + '\'' +
                "} " + super.toString();
    }
}
