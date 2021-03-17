package com.cn.configParser.importAnnotation.importConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:15
 **/
@Configuration
public class FileLogConfig {
    @Bean
    public LogService fileLogServiceImpl() {
        System.out.println("来自FileLogConfig");
        return new FileLogServiceImpl();
    }
}
