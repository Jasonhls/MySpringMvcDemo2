package com.cn.socket.tcp2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-15 16:21
 **/
public class SocketServerM {
    public static void main(String[] args) throws IOException {
        int port = 7000;
        int clientNo = 1;
        //在端口上创建一个服务器套接字
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //监听来自客户端的请求
        Socket socket = serverSocket.accept();
        try{
            /**
             * 使用线程池，接受多个客户端的数据，单独用一个独立的线程去处理每个客户端的请求
             */
            executorService.execute(new SingleServer(socket, clientNo));
        }finally {
            serverSocket.close();
        }
    }
}
