package com.cn.socketAndNetty3.nio.file;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: helisen
 * @Date 2021/9/2 15:03
 * @Description:
 */
public class FileIOTest {
    public static void main(String[] args) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream("C:\\Users\\EDZ\\Desktop\\io_test\\io_test1.txt"));
            byte[] buf = new byte[1024];
            int len;
            while((len = is.read(buf)) != -1) {
                System.out.println("len：" + len);
                for (int i = 0;i < len;i++) {
                    System.out.println((char)buf[i]);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
