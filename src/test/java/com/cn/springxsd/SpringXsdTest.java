package com.cn.springxsd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-12 09:47
 **/
public class SpringXsdTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/xml/springxsd-test.xml");
        Student student = applicationContext.getBean(Student.class);
        System.out.println(student);
    }

}
