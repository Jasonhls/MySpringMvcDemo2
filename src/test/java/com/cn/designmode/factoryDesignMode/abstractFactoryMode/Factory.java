package com.cn.designmode.factoryDesignMode.abstractFactoryMode;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 14:13
 **/
public class Factory implements IFactory{
    @Override
    public IProduct1 createProduct1() {
        return new Product1();
    }

    @Override
    public IProduct2 createProduct2() {
        return new Product2();
    }
}
