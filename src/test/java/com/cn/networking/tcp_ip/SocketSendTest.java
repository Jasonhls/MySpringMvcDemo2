package com.cn.networking.tcp_ip;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/10/8 11:31
 * @Description:
 */
public class SocketSendTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 9000);
        OutputStream out = socket.getOutputStream();

        out.write("some data".getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
        socket.close();
    }
}
