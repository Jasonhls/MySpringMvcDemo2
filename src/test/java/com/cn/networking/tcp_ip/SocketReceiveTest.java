package com.cn.networking.tcp_ip;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: helisen
 * @Date 2021/10/8 11:35
 * @Description:
 */
public class SocketReceiveTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();

        byte[] arr = new byte[5];
        int len;
        while((len = in.read(arr)) != -1) {
            String str = new String(arr, 0, len);
            System.out.println(str);
        }

        in.close();
        socket.close();
        serverSocket.close();
    }
}
