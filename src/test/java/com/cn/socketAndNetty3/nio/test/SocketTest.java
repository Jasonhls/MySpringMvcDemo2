package com.cn.socketAndNetty3.nio.test;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/10/8 11:13
 * @Description:
 */
public class SocketTest {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("baidu.com", 80);
        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("some data".getBytes(StandardCharsets.UTF_8));

        outputStream.flush();
        outputStream.close();

        socket.close();
    }
}
