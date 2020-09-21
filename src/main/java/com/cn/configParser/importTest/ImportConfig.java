package com.cn.configParser.importTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-21 15:06
 **/
@Configuration
@Import(Apple.class)
/**
 * 导入配置类，相当于   <import resource="importResourceTest.xml"/>
 */
@ImportResource(value = {"/beans/importResourceTest.xml"})
public class ImportConfig {
}
