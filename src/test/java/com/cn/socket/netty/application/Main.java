package com.cn.socket.netty.application;

import com.cn.socket.netty.application.server.NettyMyServer;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:33
 **/
public class Main {
    public static void main(String[] args) {
        try {
            String host = "127.0.0.1";
            int port = 12345;
            /**
             * 启动服务端
             */
            NettyMyServer server = new NettyMyServer(port);
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
