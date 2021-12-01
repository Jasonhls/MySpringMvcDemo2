package com.cn.jackson.study2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:44
 * @Description:
 */
public class ItemDeserializerDemo {
    public static void main(String[] args) throws IOException {
        String str = "{\"id\":1,\"itemName\":\"theItem\",\"owner\":2}";
//        test1(str);
        test2(str);
    }

    private static void test2(String str) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, new ItemDeserializer());
        mapper.registerModule(module);
        Item item = mapper.readValue(str, Item.class);
        System.out.println(item);
    }

    private static void test1(String str) throws IOException {
        Item item = new ObjectMapper().readValue(str, Item.class);
        System.out.println(item);
    }
}
