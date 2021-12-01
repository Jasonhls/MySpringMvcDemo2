package com.cn.jackson.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: helisen
 * @Date 2021/12/1 14:09
 * @Description:
 */
public class JacksonDemo {
    public static void main(String[] args) throws Exception {
        jackTest();
        jsonTest();
    }

    private static void jsonTest() throws IOException {
        String json = "{\"id\":1,\"name\":\"curry\",\"age\":30,\"birthday\":590774400000,\"email\":\"138@163.com\"}";
        ObjectMapper mapper = new ObjectMapper();
        Student student = mapper.readValue(json, Student.class);
        System.out.println(student);
    }

    private static void jackTest() throws ParseException, JsonProcessingException {
        Student u = new Student();
        u.setId(1);
        u.setName("curry");
        u.setAge(30);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        u.setBirthday(dateFormat.parse("1988-9-21"));
        u.setEmail("138@163.com");

        /**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         */
        ObjectMapper mapper = new ObjectMapper();
        String jsonValue = mapper.writeValueAsString(u);
        System.out.println(jsonValue);

        Student u2 = new Student();
        u2.setId(2);
        u2.setName("KD");
        u2.setAge(29);
        u2.setBirthday(dateFormat.parse("1989-9-21"));
        u2.setEmail("123@qq.com");


        List<Student> students = new ArrayList<>();
        students.add(u);
        students.add(u2);
        String jsonList = mapper.writeValueAsString(students);
        System.out.println(jsonList);
    }
}
