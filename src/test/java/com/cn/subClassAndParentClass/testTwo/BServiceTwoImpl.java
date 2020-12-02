package com.cn.subClassAndParentClass.testTwo;

import com.cn.test.simple.beans.TestBean;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 17:24
 **/
public class BServiceTwoImpl extends B<TestBean> implements IBService{
    @Override
    public String eat(String name) {
        return null;
    }
}
