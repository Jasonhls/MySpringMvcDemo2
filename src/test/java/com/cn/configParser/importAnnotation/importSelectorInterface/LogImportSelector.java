package com.cn.configParser.importAnnotation.importSelectorInterface;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:33
 **/
public class LogImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {"com.cn.configParser.importAnnotation.importSelectorInterface.AppConfig",
        "com.cn.configParser.importAnnotation.importConfiguration.LogParentConfig"};
    }
}
