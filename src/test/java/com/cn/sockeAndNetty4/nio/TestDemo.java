package com.cn.sockeAndNetty4.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: 何立森
 * @Date: 2023/07/26/15:09
 * @Description:
 */
public class TestDemo {
    public static void main(String[] args) throws IOException {
        //1、打开一个ServerSocketChannel监听
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //2、绑定监听的IP地址与端口号
        ssc.bind(new InetSocketAddress("127.0.0.1", 8888));
        //也可以这样绑定
        ssc.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        //3、监听客户端连接
        while(true) {
            //不断尝试获取客户端的socket连接
            SocketChannel sc = ssc.accept();
            //如果为null则代表没有连接到来，非空代表有连接
            if(sc != null) {
                //处理客户端连接
            }
        }
    }
}
