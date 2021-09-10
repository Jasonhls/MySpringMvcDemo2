package com.cn.socketAndNetty3.io;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/8/31 10:32
 * @Description:
 */
public class IO1 {
    //创建文件
    @Test
    public void test1() throws IOException {
        String path = "C:\\Users\\EDZ\\Desktop\\io_test\\1_pic.jpg";
        File src = new File(path);
        if(!src.exists()) {
            boolean newFile = src.createNewFile();
            System.out.println(newFile ? "新建成功" : "新建失败");
        }else {
            System.out.println("文件已经存在！");
        }
    }

    //读取文件
    @Test
    public void test2() throws IOException {
        String path = "C:\\Users\\EDZ\\Desktop\\io_test\\test.txt";
        File src = new File(path);
        InputStream is = new FileInputStream(src);
        byte[] data = new byte[1024];
        int len = 0;
        while((len = is.read(data)) != -1) {
            System.out.println(new String(data, 0, len));
        }
        is.close();
    }

    //写出文件
    @Test
    public void test3() throws IOException {
        String path = "C:\\Users\\EDZ\\Desktop\\io_test\\test2.txt";
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        String msg = "我买了小米！\r\n哈哈";
        fos.write(msg.getBytes(StandardCharsets.UTF_8));
        fos.flush();
        fos.close();
    }

    //文件拷贝
    @Test
    public void test4() throws IOException {
        File src = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test.txt");
        File dest = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test3.txt");
        InputStream is = new FileInputStream(src);
        OutputStream os = new FileOutputStream(dest);
        byte[] arr = new byte[1024];
        int len;
        while((len = is.read(arr)) != -1) {
            os.write(arr, 0, len);
            os.flush();
        }

        os.close();
        is.close();
    }
}
