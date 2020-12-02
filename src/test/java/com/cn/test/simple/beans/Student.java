package com.cn.test.simple.beans;

import java.math.BigDecimal;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-27 09:30
 **/
public class Student {
    private String name;
    private Integer age;
    private BigDecimal money;

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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String sayHello() {
        return this.name + " say hello to the world";
    }
}
