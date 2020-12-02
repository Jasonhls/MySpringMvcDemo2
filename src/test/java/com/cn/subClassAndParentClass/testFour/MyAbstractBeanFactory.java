package com.cn.subClassAndParentClass.testFour;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-02 10:57
 **/
public abstract class MyAbstractBeanFactory implements MyBeanFactory{
    @Override
    public String doGetBean() {
        return getBean();
    }

    protected String getBean() {
        System.out.println("开始执行");
        String s = sayHello();
        return s;
    }

    protected String sayHello() {
        System.out.println("执行父类的sayHello方法");
        return "hls";
    }

}
