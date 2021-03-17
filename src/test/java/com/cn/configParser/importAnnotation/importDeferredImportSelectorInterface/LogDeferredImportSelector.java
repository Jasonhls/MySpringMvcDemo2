package com.cn.configParser.importAnnotation.importDeferredImportSelectorInterface;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 15:13
 **/
public class LogDeferredImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {"com.cn.configParser.importAnnotation.importSelectorInterface.AppConfig",
                "com.cn.configParser.importAnnotation.importConfiguration.LogParentConfig"};
    }
}
