package com.cn.subClassAndParentClass.testFour;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-02 11:01
 **/
public abstract class MyAbstractAutowireCapableBeanFactory extends MyAbstractBeanFactory{
    @Override
    protected String sayHello() {
        System.out.println("先执行子类的sayHello方法");
        return "tom";
        //如果这里不调用父类的sayHello方法，那么就不会执行父类对的sayHello方法
//        return super.sayHello();
    }
}
