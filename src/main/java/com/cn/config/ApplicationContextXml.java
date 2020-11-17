package com.cn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * @description: 代替applicationContext.xml文件的，采用配置的方式
 * @author: helisen
 * @create: 2020-07-23 14:01
 **/
@Configuration
@ComponentScan(basePackages = {"com.cn"},
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
    })
public class ApplicationContextXml {

    /*@Bean
    public DataSource jdbcTemplate() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("localhost:3306/test");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }*/

}
