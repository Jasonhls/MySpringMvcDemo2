package com.cn.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 通过set方法注入bean
 * @author: helisen
 * @create: 2020-11-20 14:42
 **/
@Component
public class AutowiredBySetter {

    private AutowiredDTO autowiredDTO;

    @Autowired
    public void setAutowiredDTO(AutowiredDTO autowiredDTO) {
        this.autowiredDTO = autowiredDTO;
    }

    @Override
    public String toString() {
        return "AutowiredBySetMethod{" +
                "autowiredDTO=" + autowiredDTO +
                '}';
    }
}
