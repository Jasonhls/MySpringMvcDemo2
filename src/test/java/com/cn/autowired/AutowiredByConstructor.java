package com.cn.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 通过构造方法注入bean
 * @author: helisen
 * @create: 2020-11-20 14:24
 **/
@Component
public class AutowiredByConstructor {

    private AutowiredDTO autowiredDTO;

    @Autowired
    public AutowiredByConstructor(AutowiredDTO autowiredDTO) {
        this.autowiredDTO = autowiredDTO;
    }

    @Override
    public String toString() {
        return "AutowiredByConstructor{" +
                "autowiredDTO=" + autowiredDTO +
                '}';
    }
}
