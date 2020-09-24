package com.cn.configParser;

import com.cn.configParser.componentScanTest.MyConfig;
import com.cn.configParser.componentScanTest.Student;
import com.cn.configParser.importAndBeanTest.Apple;
import com.cn.configParser.importAndBeanTest.ImportConfig;
import com.cn.configParser.importAndBeanTest.location.Banana;
import com.cn.configParser.propertySourceConfig.scanPackage.DBConnection;
import com.cn.configParser.propertySourceConfig.EnvironmentPropertySourceConfig;
import com.cn.configParser.propertySourceConfig.TestBean;
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
        Apple apple = (Apple) context.getBean("com.cn.configParser.importAndBeanTest.Apple");
        System.out.println(apple.getName() + "; " + apple.getColor() + "; " + apple.getTaste());
        Banana banana = (Banana) context.getBean("banana");
        System.out.println(banana.getName() + "; " + banana.getProducingArea());
    }

    /**
     * 测试注解@PropertySource与Environment一起用
     */
    @Test
    public void testPropertySourceAndEnvironment() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EnvironmentPropertySourceConfig.class);
        TestBean tb = (TestBean) context.getBean("testBean");
        System.out.println(tb.toString());
    }

    /**
     * 测试注解@PropertySource与@Value一起用
     */
    @Test
    public void testPropertySourceAndValue() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.cn.configParser.propertySourceConfig.scanPackage");
        DBConnection dbConnection =  context.getBean(DBConnection.class);
        dbConnection.printDBConfigs();
    }
}
