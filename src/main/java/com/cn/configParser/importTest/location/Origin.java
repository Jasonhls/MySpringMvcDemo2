package com.cn.configParser.importTest.location;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-21 15:08
 **/
@Component
public class Origin {
    private String name;
    private String taste;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Override
    public String toString() {
        return "Origin{" +
                "name='" + name + '\'' +
                ", taste='" + taste + '\'' +
                '}';
    }
}
