package com.cn.configParser.importAnnotation.importSelectorInterface2;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:53
 **/
public class LogImportSelector2 implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableLogService.class.getName(), false);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
        System.out.println(attributes);

        String logType = attributes.getString("logType");

        if(logType.equalsIgnoreCase("StdOut")) {
            return new String[] {"com.cn.configParser.importAnnotation.importSelectorInterface.AppConfig",
            "com.cn.configParser.importAnnotation.importConfiguration.StdOutConfig"};
        }else if(logType.equalsIgnoreCase("File")) {
            return new String[] {"com.cn.configParser.importAnnotation.importSelectorInterface.AppConfig",
            "com.cn.configParser.importAnnotation.importConfiguration.FileLogConfig"};
        }else if(logType.equalsIgnoreCase("MySql")) {
            return new String[] {"com.cn.configParser.importAnnotation.importSelectorInterface.AppConfig",
            "com.cn.configParser.importAnnotation.importConfiguration.MySqlLogConfig"};
        }else {
            return new String[] {"com.cn.configParser.importAnnotation.importSelectorInterface.AppConfig",
                    "com.cn.configParser.importAnnotation.importConfiguration.LogParentConfig"};
        }
    }
}
