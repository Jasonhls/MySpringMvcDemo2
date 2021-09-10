package com.cn.aop.aopTest;

import org.junit.Test;

public class AopTest {
    @Test
    public void testAop() {
        Person p = new Student();
        MyPlugin myPlugin = new MyPlugin();
        p = (Person) myPlugin.plugin(p);
        p.eat();
    }
}
