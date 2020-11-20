package com.cn.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 通过set方法注入bean
 * @author: helisen
 * @create: 2020-10-26 09:09
 **/
@Component
public class AutowiredBySetterTwo {
    private List<AutowiredDTO> list = new ArrayList<>();

    /**
     * 这样会多一个属性list
     * @param autowiredDTO
     */
    @Autowired
    public void setList(AutowiredDTO autowiredDTO) {
        if(autowiredDTO != null) {
            list.add(autowiredDTO);
        }
    }
}
