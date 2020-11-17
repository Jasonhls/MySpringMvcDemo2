package com.cn.designmode.factoryDesignMode.simpleFactoryMode;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 11:18
 **/
public class SimpleNoodlesFactory {
    public static final int TYPE_LZ = 1; //兰州拉面
    public static final int TYPE_PM= 2; //泡面
    public static final int TYPE_GK = 3; //干干扣面

    public static INoodles createNoodles(int type) {
        switch (type) {
            case TYPE_LZ:
                return new LZNoodles();
            case TYPE_PM:
                return new PaoNoodles();
            case TYPE_GK:
            default:
                return new GankouNoodles();
        }
    }
}
