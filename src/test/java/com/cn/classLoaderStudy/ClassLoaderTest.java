package com.cn.classLoaderStudy;

import org.junit.Test;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-15 13:51
 **/
public class ClassLoaderTest {
    @Test
    public void test1() {
        //下面这个行代码是Launcher中的，可以获取BootstrapClassLoader加载的jar包路径
        String bootClassPath = System.getProperty("sun.boot.class.path");
//        printPath(bootClassPath);

        //ExtClassLoader加载的jar包路径
        String extClassPath = System.getProperty("java.ext.dirs");
//        printPath(extClassPath);

        //AppClassLoader加载的jar包路径
        String appClassPath = System.getProperty("java.class.path");
//        printPath(appClassPath);
    }

    public static void printPath(String path){
        String[] split = path.split(";");
        for (String str : split) {
            System.out.println(str);
        }
    }


    /**
     * 1.一个AppClassLoader查找资源时，先看看缓存是否有，缓存有从缓存中获取，否则委托给父加载器。
     * 2.递归，重复第1部的操作。
     * 3.如果ExtClassLoader也没有加载过，则由Bootstrap ClassLoader出面，它首先查找缓存，如果没有找到的话，就去找自己的规定的路径下，也就是sun.mic.boot.class下面的路径。找到就返回，没有找到，让子加载器自己去找。
     * 4.Bootstrap ClassLoader如果没有查找成功，则ExtClassLoader自己在java.ext.dirs路径中去查找，查找成功就返回，查找不成功，再向下让子加载器找。
     * 5.ExtClassLoader查找不成功，AppClassLoader就自己查找，在java.class.path路径下查找。找到就返回。如果没有找到就让子类找，如果没有子类会怎么样？抛出各种异常
     */
    @Test
    public void test2() {
        /**
         * AppClassLoader的parent是ExtClassLoader，ExtClassLoader的parent是null
         * getParent()其实返回的是一个ClassLoader对象parent，parent赋值是在ClassLoader对象的构造方法中，它有两种情况：
         * 1.由外部类创建ClassLoader时直接指定一个ClassLoader为parent
         * 2.由getSystemClassLoader()方法生成，也就是在sun.misc.Launcher通过getClassLoader()获取，也就是AppClassLoader。直白地说，
         * 一个ClassLoader创建时如果没有指定parent，那么它的parent默认就是AppClassLoader。
         *
         * BootstrapClassLoader是JVM内置的类加载器，Java中没有
         * 在Launcher的构造函数中，Launcher.AppClassLoader.getAppClassLoader(var1);这行代码会通过构造方法创建AppClassLoader,并且会把ExtClassLoader传进去并赋值给它的parent，因此AppClassLoader的parent为ExtClassLoader
         * 在Launcher构造函数中，Launcher.ExtClassLoader.getExtClassLoader();这行代码会通过构造方法创建ExtClassLoader对象，在该构造方法中，会把null赋值给parent，因此ExtClassLoader的parent为null
         */
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        //sun.misc.Launcher$AppClassLoader@18b4aac2
        System.out.println(classLoader.toString());

        //sun.misc.Launcher$ExtClassLoader@36d64342
        System.out.println(classLoader.getParent().toString());

        System.out.println(classLoader.getParent().getParent().toString());

//        ClassLoader classLoader1 = String.class.getClassLoader();
//        System.out.println(classLoader1);

    }
}
