package com.cn.configParser.importAnnotation.importSelectorInterface;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:34
 **/
@Configuration
@Import(value = {LogImportSelector.class})
public class LogImportSelectorConfig {
}
