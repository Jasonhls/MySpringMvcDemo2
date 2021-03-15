package com.cn.socketAndNetty.netty.application;

import com.cn.socketAndNetty.netty.application.server.NettyMyServer;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:33
 **/
public class MainServer {
    public static void main(String[] args) {
        try {
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
