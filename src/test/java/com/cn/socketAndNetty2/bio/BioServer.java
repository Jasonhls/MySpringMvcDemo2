package com.cn.socketAndNetty2.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-12 16:59
 **/
public class BioServer {
    public static void main(String[] args) throws Exception{
        //思路
        //1、创建一个线程池
        //2、如果有客户端连接，就创建一个线程，与之通信（单独写一个方法）
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端启动了");
        while (true) {
            System.out.println("线程id=" + Thread.currentThread().getId() + "，线程名称=" + Thread.currentThread().getName());
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");

            //创建一个线程，与之通讯
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯
                    handle(socket);
                }
            });
        }

    }

    //编写一个handler方法，与客户端通讯
    public static void handle(Socket socket) {
        try {
            System.out.println("handle方法中，线程id=" + Thread.currentThread().getId() + "，线程名称=" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while(true) {
                int read = inputStream.read(bytes);
                if(read != -1) {
                    //输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                }else {
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
