package com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 15:36
 **/
public class CustomImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar , ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //获取
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(CustomComponentScan.class.getName(), false);
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);
        System.out.println("注解CustomComponentScan中各属性的值分别为：" + attributes);

        String[] basePackages = (String[]) attributes.get("basePackages");
        System.out.println("要扫描的包是：" + Arrays.toString(basePackages));

        if(basePackages == null || basePackages.length == 0) {
            String basePackage = null;
            try {
                basePackage = Class.forName(annotationMetadata.getClassName()).getPackage().getName();
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
            basePackages = new String[] {basePackage};
        }

        MapperBeanDefinitionScanner scanner = new MapperBeanDefinitionScanner(beanDefinitionRegistry, false);
        scanner.setResourceLoader(resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter(CustomComponent.class));
        scanner.doScan(basePackages);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public class MapperBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

        public MapperBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
            super(registry, useDefaultFilters);
        }

        protected void registerFilters() {
            addIncludeFilter(new AnnotationTypeFilter(CustomComponent.class));
        }

        @Override
        protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
            return super.doScan(basePackages);
        }
    }
}
