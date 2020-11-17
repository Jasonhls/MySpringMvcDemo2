package com.cn.resources;

import org.junit.Test;

import java.io.InputStream;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-05 15:25
 **/
public class ResourcesTest {
    @Test
    public void test1() {
        //得到的其实AppClassLoader，AppClassLoader是类Launch的内部类AppClassLoader
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream is = classLoader.getResourceAsStream("1.jpg");

    }
}
