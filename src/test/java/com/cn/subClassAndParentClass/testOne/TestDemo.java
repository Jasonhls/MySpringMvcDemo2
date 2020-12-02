package com.cn.subClassAndParentClass.testOne;

import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 10:36
 **/
public class TestDemo {
    @Test
    public void testDemoOne() {
        MyGrandParent m = new My();
        m.getTest();
    }

}
