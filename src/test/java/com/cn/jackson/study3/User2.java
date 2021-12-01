package com.cn.jackson.study3;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:52
 * @Description:
 */
public class User2 {
    private Integer age;
    private String name;
    private String hobby;
    private String email;

    public User2() {
    }

    public User2(Integer age, String name, String hobby, String email) {
        this.age = age;
        this.name = name;
        this.hobby = hobby;
        this.email = email;
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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User2{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", hobby='" + hobby + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
