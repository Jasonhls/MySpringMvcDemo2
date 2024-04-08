package com.cn.sockeAndNetty4.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/15:37
 * @Description:
 */
public class SimpleNettyClient {
    public void connect(String host, int port) throws Exception {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            //客户端启动类程序
            Bootstrap bootstrap = new Bootstrap();
            /**
             * EventLoop的组
             */
            bootstrap.group(worker);
            /**
             * 用于构造socketChannel工厂
             */
            bootstrap.channel(NioSocketChannel.class);
            /**
             * 设置选项
             */
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            /**
             * 自定义客户端Handler
             */
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new SimpleNettyClientHandler());
                }
            });

            //开启客户端监听，连接到远程阶段，阻塞等待直到连接完成
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            //阻塞等待数据，直到channel关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        SimpleNettyClient client = new SimpleNettyClient();
        client.connect("127.0.0.1", 8088);
    }
}
