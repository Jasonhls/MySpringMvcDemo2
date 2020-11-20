package com.cn.autowired;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-26 09:08
 **/
@Component
public class AutowiredDTO {
    private String name;
    private Integer age;
    private List<String> hobbies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }
}
