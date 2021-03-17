package com.cn.configParser.importAnnotation.importConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:13
 **/
@Configuration
public class StdOutConfig {
    @Bean
    public LogService stdOutLogServiceImpl() {
        return new StdOutLogServiceImpl();
    }
}
