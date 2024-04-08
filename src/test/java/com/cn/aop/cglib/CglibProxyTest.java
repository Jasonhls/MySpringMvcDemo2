package com.cn.aop.cglib;

import org.springframework.cglib.core.DebuggingClassWriter;

/**
 * @Author: 何立森
 * @Date: 2024/04/03/14:18
 * @Description:
 */
public class CglibProxyTest {

    public static void main(String[] args) {
        // 生成class类的路径，指定的项目的aopClasses这个文件夹下，
        //生成了public class Student$$EnhancerByCGLIB$$87dbca97 extends Student implements Factory，生成的这个类中，
        //可以看到eat方法中会调用intercept方法，实现代理效果。
        //可以指定具体的路径，如System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E://tmp");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "aopClasses");

        Student student = new Student();
        CglibProxyFactory cglibProxyFactory = new CglibProxyFactory(student);
        Student studentProxy = (Student)cglibProxyFactory.getProxyObject();
        studentProxy.eat();
    }
}
