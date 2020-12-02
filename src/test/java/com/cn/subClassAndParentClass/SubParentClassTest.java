package com.cn.subClassAndParentClass;

import com.cn.subClassAndParentClass.testFour.MyBeanFactory;
import com.cn.subClassAndParentClass.testFour.MyDefaultListableBeanFactory;
import com.cn.subClassAndParentClass.testTwo.A;
import com.cn.subClassAndParentClass.testTwo.BServiceImpl;
import com.cn.subClassAndParentClass.testTwo.BServiceTwoImpl;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-26 15:47
 **/
public class SubParentClassTest {

    /**
     * 返回自身所有的public方法，父类所有的public方法，以及实现的接口方法
     */
    @Test
    public void testMethods() {
        print(A.class.getMethods());

        print(BServiceImpl.class.getMethods());

        print(BServiceTwoImpl.class.getMethods());
    }

    @Test
    public void testDeclaredMethods() {
        print(A.class.getDeclaredMethods());

        /**
         * getDeclaredMethods()方法返回自己的所有方法，然后接口的方法，不会返回父类方法
         */
        print(BServiceImpl.class.getDeclaredMethods());

        /**
         * 如果父类没有public关键字修饰，那么子类的getDeclaredMethods()会返回父类的所有公共方法，
         * 但是如果有泛型，且子类指定了具体的泛型，那么返回泛型的公共方法不会打印出来
         */
        print(BServiceTwoImpl.class.getDeclaredMethods());
    }

    private void print(Method[] declaredMethods) {
        for (Method m : declaredMethods) {
            System.out.println(m.getName());
        }
        System.out.println("---------------------------");
    }

    /**
     * factory为MyDefaultListableBeanFactory类型，
     */
    @Test
    public void test() {
        MyBeanFactory factory = new MyDefaultListableBeanFactory();
        factory.doGetBean();
    }
}
