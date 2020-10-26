package com.cn.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-26 09:09
 **/
@Component
public class TeacherDemo {
    private List<StudentDemo> list = new ArrayList<>();

    /**
     * 这样会多一个属性list
     * @param studentDemo
     */
    @Autowired
    public void setList(StudentDemo studentDemo) {
        if(studentDemo != null) {
            list.add(studentDemo);
        }
    }
}
