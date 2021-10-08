package com.cn.socketAndNetty3.nio.test;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: helisen
 * @Date 2021/10/8 11:07
 * @Description:
 */
public class ServerSocketTest {
    public static void main(String[] args) throws Exception{
        /*ServerSocket serverSocket = new ServerSocket(9000);
        boolean isStopped = false;
        while(!isStopped) {
            Socket clientSocket = serverSocket.accept();
        }*/
        Socket socket = new Socket("baidu.com", 80);
        InputStream is = socket.getInputStream();

        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = is.read(bytes, 0, len)) != -1) {

        }
        is.close();
        is.close();//怕冷
        System.out.println();
    }
}
