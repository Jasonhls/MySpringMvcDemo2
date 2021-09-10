package com.cn.socketAndNetty3.io;

import org.junit.Test;

import java.io.*;

/**
 * @Author: helisen
 * @Date 2021/8/31 11:14
 * @Description:
 */
public class IO2 {
    //读取纯文本文件
    @Test
    public void test1() throws IOException {
        File src = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test.txt");
        Reader reader = new FileReader(src);
        char[] arr =new char[1024];
        int len = 0;
        while((len = reader.read(arr)) != -1) {
            System.out.println(new String(arr, 0, len));
        }
    }

    //写出出文本文件
    @Test
    public void test2() throws IOException {
        File dest = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test4.txt");
        Writer writer = new FileWriter(dest);
        String str = "鲁班，你好吗？";
        writer.write(str);
        writer.append("哈哈，吃中饭了！");
        writer.flush();
        writer.close();
    }

    //文件拷贝
    @Test
    public void test3() throws IOException {
        File src = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test.txt");
        File dest = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test5.txt");
        Reader reader = new FileReader(src);
        Writer writer = new FileWriter(dest);
        char[] arr = new char[1024];
        int len;
        while((len = reader.read(arr)) != -1) {
            writer.write(arr, 0, len);
            writer.flush();
        }

        writer.close();
        reader.close();
    }
}
