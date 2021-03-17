package com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface;

import java.lang.annotation.*;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 15:39
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomComponent {
}
