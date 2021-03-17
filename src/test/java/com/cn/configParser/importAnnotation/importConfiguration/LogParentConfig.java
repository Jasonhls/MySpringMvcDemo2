package com.cn.configParser.importAnnotation.importConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:15
 **/
@Configuration
@Import(value = {StdOutConfig.class, FileLogConfig.class, MySqlLogConfig.class})
public class LogParentConfig {
}
