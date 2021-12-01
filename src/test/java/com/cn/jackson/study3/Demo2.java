package com.cn.jackson.study3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:53
 * @Description:
 */
public class Demo2 {
    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
//        test1(mapper);  //少属性对象的json串反序列化给到多属性的对象，可以成功
        test2(mapper);   //多属性对象的json串反序列化给到少属性的对象，解决办法：配置mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        test3(mapper);
    }

    private static void test3(ObjectMapper mapper) throws IOException {
        User2 u2 = new User2(30, "哈哈", "打篮球", "123@qq.com");
        String s = mapper.writeValueAsString(u2);
        System.out.println(s);
    }

    private static void test2(ObjectMapper mapper) throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        User2 u2 = new User2(30, "哈哈", "打篮球", "123@qq.com");
        String s = mapper.writeValueAsString(u2);
        System.out.println(s);


        User1 user1 = mapper.readValue(s, User1.class);
        System.out.println(user1);
    }

    private static void test1(ObjectMapper mapper) throws IOException {
        User1 u1 = new User1(30, "哈哈");
        String s = mapper.writeValueAsString(u1);
        System.out.println(s);


        User2 user2 = mapper.readValue(s, User2.class);
        System.out.println(user2);
    }
}
