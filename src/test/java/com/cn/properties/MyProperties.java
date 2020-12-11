package com.cn.properties;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-09 11:46
 **/
public class MyProperties {
    public static final String PATH = "MyProperties.properties";
    public static final Properties properties;
    static {
        try {
            ClassPathResource resource = new ClassPathResource("com.cn.properties/MyProperties.properties");
            properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("can not load " + PATH);
        }
    }

    public String getValue(String key) {
        String value = properties.getProperty(key);
        return value;
    }

    @Test
    public void test() {
        MyProperties my = new MyProperties();
        String value = my.getValue("name");
        System.out.println(value);
    }

}
