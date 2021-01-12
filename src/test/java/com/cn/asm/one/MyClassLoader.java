package com.cn.asm.one;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-11 11:25
 **/
public class MyClassLoader extends ClassLoader{
    public MyClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    /**
     * 根据字节码的byte数组生成Class文件
     * @param name
     * @param data
     * @return
     */
    public Class<?> defineClassForName(String name, byte[] data) {
        return this.defineClass(name, data, 0, data.length);
    }
}
