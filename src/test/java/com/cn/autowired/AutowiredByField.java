package com.cn.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 通过属性注入bean
 * @author: helisen
 * @create: 2020-11-20 14:38
 **/
@Component
public class AutowiredByField {
    @Autowired
    private AutowiredDTO autowiredDTO;

    @Override
    public String toString() {
        return "AutowiredByProperty{" +
                "autowiredDTO=" + autowiredDTO +
                '}';
    }
}
