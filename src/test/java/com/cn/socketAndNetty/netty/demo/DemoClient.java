package com.cn.socketAndNetty.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 16:30
 **/
public class DemoClient {
    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 添加拆包器，需要在添加handler之前添加
                             */
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                                    Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));
                            /**
                             * 客户端配置心跳包
                             */
                            socketChannel.pipeline().addLast(new IdleStateHandler(5, 5, 10));
                            /**
                             * 添加handler
                             */
                            socketChannel.pipeline().addLast(new DemoClientHandler());
                        }
                    });

            //start the client
            ChannelFuture f = b.connect("localhost", 8008).sync();
            if(f.isSuccess()) {
                System.out.println("client，连接服务端成功");
            }

            // Wait until the connection is closed
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
