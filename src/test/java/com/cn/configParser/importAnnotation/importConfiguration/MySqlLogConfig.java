package com.cn.configParser.importAnnotation.importConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:14
 **/
@Configuration
public class MySqlLogConfig {
    @Bean
    public LogService mySqlLogServiceImpl() {
        return new MySqlLogServiceImpl();
    }
}
