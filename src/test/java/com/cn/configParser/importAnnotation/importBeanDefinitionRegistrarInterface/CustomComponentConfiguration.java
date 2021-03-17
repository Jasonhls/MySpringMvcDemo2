package com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface;

import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 15:49
 **/
@Configuration
@CustomComponentScan(basePackages = {"com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface"})
public class CustomComponentConfiguration {
}
