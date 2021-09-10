package com.cn.socketAndNetty3.io;

import org.junit.Test;

import java.io.*;

/**
 * @Author: helisen
 * @Date 2021/8/31 11:25
 * @Description:
 */
public class IO3 {
    //缓冲字节流
    @Test
    public void test1() throws IOException{
        File src = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test.txt");
        File dest = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test6.txt");
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(dest));
        byte[] arr = new byte[1024];
        int len;
        while((len = is.read(arr)) != -1) {
            os.write(arr, 0, len);
            os.flush();
        }

        os.close();
        is.close();
    }

    //缓冲字符流
    @Test
    public void test2() throws IOException{
        File src = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test.txt");
        File dest = new File("C:\\Users\\EDZ\\Desktop\\io_test\\test7.txt");
        BufferedReader is = new BufferedReader(new FileReader(src));
        BufferedWriter os = new BufferedWriter(new FileWriter(dest));
        String line;
        while((line = is.readLine()) != null) {
            os.write(line);
            os.newLine();
        }

        os.close();
        is.close();
    }
}
