package com.cn.config.otherPackage;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author admin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Documented
@Import(MyCustomConfig.class)
public @interface EnableOtherConfig {
}
