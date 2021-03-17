package com.cn.configParser.importAnnotation.importDeferredImportSelectorInterface;

import com.cn.configParser.importAnnotation.importConfiguration.FileLogServiceImpl;
import com.cn.configParser.importAnnotation.importConfiguration.LogService;
import com.cn.configParser.importAnnotation.importSelectorInterface.LogImportSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:  ImportSelector与DeferredImportSelector的比较
 * @author: helisen
 * @create: 2021-03-17 15:14
 **/
@Configuration
/**
 * 导入实现了DeferredImportSelector的子类，导入名字为fileLogServiceImpl的bean到spring中，
 * 会优先走自定义DeferredImportSelector子类中selectImports方法
 * 返回路径对应的配置文件，而忽略主配置文件
 */
//@Import(value = {LogDeferredImportSelector.class})
/**
 *  导入了实现ImportSelector接口的子类，导入名字为fileLogServiceImpl的bean到spring中，会走主配置文件中的
 */
@Import(value = {LogImportSelector.class})
public class LogDeferredImportSelectorConfig {
    @Bean
    public LogService fileLogServiceImpl() {
        System.out.println("来自主配置文件LogDeferredImportSelectorConfig");
        return new FileLogServiceImpl();
    }
}
