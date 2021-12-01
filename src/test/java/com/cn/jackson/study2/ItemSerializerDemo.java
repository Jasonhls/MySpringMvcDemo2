package com.cn.jackson.study2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:37
 * @Description:
 */
public class ItemSerializerDemo {
    public static void main(String[] args) throws JsonProcessingException {
//        test1();
        /**
         * 可以使用自定义的JsonSerializer，见方法test2，可以使用在对象上面加注解@JsonSerialize(using = ItemSerializer.class)
         */
        test2();
    }

    private static void test2() throws JsonProcessingException {
        Item myItem = new Item(1, "theItem", new User(2, "theUser"));
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(module);
        String s = mapper.writeValueAsString(myItem);
        System.out.println(s);
    }

    private static void test1() throws JsonProcessingException {
        Item myItem = new Item(1, "theItem", new User(2, "theUser"));
        String s = new ObjectMapper().writeValueAsString(myItem);
        System.out.println(s);
    }
}
