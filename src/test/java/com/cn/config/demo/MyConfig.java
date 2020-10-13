package com.cn.config.demo;

import com.cn.config.otherPackage.EnableOtherConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-13 17:27
 **/
@Configuration
@ComponentScan(value = {"com.cn.config.demo"})
@EnableOtherConfig
public class MyConfig {
}
