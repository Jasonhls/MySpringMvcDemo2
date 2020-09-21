package com.cn.configParser;

import com.cn.configParser.componentScanTest.MyConfig;
import com.cn.configParser.componentScanTest.Student;
import com.cn.configParser.importTest.Apple;
import com.cn.configParser.importTest.ImportConfig;
import com.cn.configParser.importTest.location.Banana;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-17 17:00
 **/
public class ConfigParserTest {
    /**
     * 测试@ComponentScan，@ComponentScans
     */
    @Test
    public void testComponentScanConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Student student = (Student) context.getBean("student");
        System.out.println(student.getAge() + "; " + student.getName() + "; " +student.getSex());
    }

    /**
     * 测试注解@Import，@ImportResource
     */
    @Test
    public void testImportConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfig.class);
        Apple apple = (Apple) context.getBean("apple");
        System.out.println(apple.getName() + "; " + apple.getColor() + "; " + apple.getTaste());
        Banana banana = (Banana) context.getBean("banana");
        System.out.println(banana.getName() + "; " + banana.getProducingArea());
    }
}
