package com.cn.jackson.study3;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:51
 * @Description:
 */
public class User1 {
    private Integer age;
    private String name;

    public User1() {
    }

    public User1(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User1{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
