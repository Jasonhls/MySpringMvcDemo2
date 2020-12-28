package com.cn.classTest;

import org.junit.Test;
import org.springframework.util.ClassUtils;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-17 16:16
 **/
public class ClassTest {
    @Test
    public void test1() {
        boolean present = ClassUtils.isPresent("com.cn.autowired.AutowiredByConstructor", null);
        System.out.println(present);
    }
}
