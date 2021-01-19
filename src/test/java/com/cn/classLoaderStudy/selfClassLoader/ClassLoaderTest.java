package com.cn.classLoaderStudy.selfClassLoader;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-15 15:27
 **/
public class ClassLoaderTest {
    public static void main(String[] args) {
        DiskClassLoader diskClassLoader = new DiskClassLoader("D:\\lib");
        try {
            Class<?> c = diskClassLoader.loadClass("com.cn.classLoaderStudy.selfClassLoader.Test");
            if(c != null) {
                Object obj = c.newInstance();
                Method method = c.getDeclaredMethod("say", null);
                //通过反射调用Test类的say方法
                method.invoke(obj, null);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
