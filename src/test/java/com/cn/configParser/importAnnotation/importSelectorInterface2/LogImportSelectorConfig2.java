package com.cn.configParser.importAnnotation.importSelectorInterface2;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:59
 **/
@Configuration
//@Import(value = {LogImportSelector2.class})
@EnableLogService(logType = "file")
public class LogImportSelectorConfig2 {
}
