package com.cn.configParser.importAnnotation;

import com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface.CustomComponentConfiguration;
import com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface.CustomDataMapper;
import com.cn.configParser.importAnnotation.importConfiguration.LogParentConfig;
import com.cn.configParser.importAnnotation.importConfiguration.LogService;
import com.cn.configParser.importAnnotation.importDeferredImportSelectorInterface.LogDeferredImportSelectorConfig;
import com.cn.configParser.importAnnotation.importSelectorInterface.ApplicationProperties;
import com.cn.configParser.importAnnotation.importSelectorInterface.LogImportSelectorConfig;
import com.cn.configParser.importAnnotation.importSelectorInterface2.LogImportSelectorConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:35
 **/
public class ImportAnnotationTest {
    /**
     * 测试@Import注解，直接导入配置文件的方式
     */
    @Test
    public void testImportConfiguration() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LogParentConfig.class);
        LogService obj = (LogService) context.getBean("stdOutLogServiceImpl");
        obj.print("控制台输出");

        obj = (LogService) context.getBean("fileLogServiceImpl");
        obj.print("文件输出");

        obj = (LogService) context.getBean("mySqlLogServiceImpl");
        obj.print("数据库输出");

        context.close();
    }

    /**
     * 测试@Import注解，导入实现ImportSelector接口的方式
     */
    @Test
    public void testImportSelectorInterface() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LogImportSelectorConfig.class);
        LogService obj = (LogService) context.getBean("stdOutLogServiceImpl");
        obj.print("控制台打印");

        obj = (LogService) context.getBean("fileLogServiceImpl");
        obj.print("文件输出");

        obj = (LogService) context.getBean("mySqlLogServiceImpl");
        obj.print("数据库输出");

        ApplicationProperties applicationProperties = (ApplicationProperties) context.getBean("applicationProperties");
        System.out.println(applicationProperties);

        context.close();
    }

    /**
     * 测试@Import注解，导入实现ImportSelector接口的变种
     */
    @Test
    public void testImportSelectorInterface2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LogImportSelectorConfig2.class);
        LogService bean = context.getBean(LogService.class);
        bean.print("哈哈");

        ApplicationProperties applicationProperties = (ApplicationProperties) context.getBean("applicationProperties");
        System.out.println(applicationProperties);
    }

    /**
     * 测试@Import注解，导入实现DeferredImportSelector的方式
     */
    @Test
    public void testDeferredImportSelector() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LogDeferredImportSelectorConfig.class);
        LogService obj = (LogService) context.getBean("fileLogServiceImpl");
        obj.print("文件输出");

        ApplicationProperties applicationProperties = (ApplicationProperties) context.getBean("applicationProperties");
        System.out.println(applicationProperties);
    }

    /**
     *测试@Import注解，导入实现了ImportBeanDefinitionRegistrar接口的方式
     */
    @Test
    public void testCustomImportBeanDefinitionRegistrar() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CustomComponentConfiguration.class);
        CustomDataMapper bean = context.getBean(CustomDataMapper.class);
        System.out.println(bean);
        System.out.println(bean.printData());
    }
}
