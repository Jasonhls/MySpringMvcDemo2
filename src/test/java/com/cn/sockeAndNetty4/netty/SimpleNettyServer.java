package com.cn.sockeAndNetty4.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/15:07
 * @Description:
 */
public class SimpleNettyServer {
    public void bind(int port) throws Exception {
        //服务器端应用使用两个NioEventLoopGroup创建两个EventLoop的组，EventLoop这个相当于一个处理线程，是Netty接受请求和处理IO请求的线程。
        //主线程组，用于接受客户端的链接，但是不做任何处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组，当boss接受连接并注册被接受的连接到worker时，处理被接受连接的流量
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //netty服务器启动类的创建，辅助工具类，用于服务器通道的一系列配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /**
             * 使用了多少线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
             * 设置循环线程组，前者用于处理客户端连接事件，后者用于处理网络IO（server使用两个参数这个构造函数）
             * public ServerBootstrap group(EventLoopGroup group)
             * public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)
             */
            serverBootstrap.group(bossGroup, workerGroup) //绑定两个线程组
                    .channel(NioServerSocketChannel.class) //指定NIO的模式
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //通过SocketChannel去获得对应的管道
    //                        ChannelPipeline pipeline = socketChannel.pipeline();
                            //通过管道，添加handler
    //                        pipeline.addLast("nettyServerOutBoundHandler", new NettyServerOutBoundHandler());
    //                        pipeline.addLast("nettyServerHandler", new NettyServerHandler());
                            socketChannel.pipeline().addLast(new SimpleNettyServerHandler());
                        }
                    });

            //启动server，绑定端口，开始接受进来的连接，设置8080为启动的端口号，同时启动方式为异步
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("server start");
            //监听关闭的channel，等待服务器socket关闭。设置位同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8088;
        new SimpleNettyServer().bind(port);
    }
}
