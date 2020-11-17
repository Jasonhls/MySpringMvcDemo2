package com.cn.designmode.factoryDesignMode;

import com.cn.designmode.factoryDesignMode.simpleFactoryMode.INoodles;
import com.cn.designmode.factoryDesignMode.simpleFactoryMode.SimpleNoodlesFactory;
import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-16 11:21
 **/
public class FactoryDesignModeTest {
    @Test
    public void testSimpleFactoryOne() {
        INoodles noodles = SimpleNoodlesFactory.createNoodles(SimpleNoodlesFactory.TYPE_GK);
        noodles.desc();
    }
}
