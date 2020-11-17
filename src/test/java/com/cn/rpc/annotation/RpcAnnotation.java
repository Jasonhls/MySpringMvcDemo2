package com.cn.rpc.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcAnnotation {
    Class<?> value();
}
