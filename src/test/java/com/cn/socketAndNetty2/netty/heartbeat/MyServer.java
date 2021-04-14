package com.cn.socketAndNetty2.netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 13:37
 **/
public class MyServer {
    public static void main(String[] args) throws Exception{
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, bossGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            /**
                             加入一个netty 提供的 IdleStateHandler
                             说明：
                             1、IdleStateHandler 是netty 提供的处理空闲状态的处理器
                             2、long readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检测是否连接
                             3、long writerIdleTime：表示多长时间没有写，就会发送一个心跳检测包检测是否连接
                             4、long allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
                             5、文档说明
                             triggers on {@link IdleStateEvent} when a {@link Channel} has not performed read, write, or both operation for a while
                             6、当IdleStateEvent触发后，就会传递给管道的下一个 handler 去处理，通过调用（触发）下一个 handler 的userEventTriggered，
                             在该方法中去处理IdleStateEvent（读空闲，写空闲，读写空闲）
                             */
                            pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                            //加入一个对空闲检测进一步处理的handler（自定义）
                            pipeline.addLast(new MyServerHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
