package com.cn.configParser.importAnnotation.importSelectorInterface2;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//下面这个也可以放在配置文件中，也可以放在这个自定义注解类上
@Import(value = {LogImportSelector2.class})
public @interface EnableLogService {
    //默认日志输出到控制台
    String  logType() default "stdout";

    @AliasFor("value")
    String[] basePackages() default {};

    @AliasFor("basePackages")
    String[] value() default {};
}
