package com.cn.socketAndNetty.fileDownload;

import java.io.*;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 14:21
 **/
public class MyReader {
    public static void main(String[] args) throws Exception {
        //字节输入流
        InputStream is = new FileInputStream(new File("in.jpg"));
        //字节输出流
        OutputStream os = new FileOutputStream(new File("out.jpg"));
        //字符输入流，FileReader是InputStreamReader的子类
        Reader fileReader = new FileReader(new File("in.jpg"));
        //字符输出流，FileWriter是OutputStreamWriter的子类
        Writer fileWriter = new FileWriter(new File("out.jpg"));
        //字节输入流转换为字符输入流
        Reader reader = new InputStreamReader(is);
        //字节输出流转换为字符输出流
        Writer writer = new OutputStreamWriter(os);

    }
}
