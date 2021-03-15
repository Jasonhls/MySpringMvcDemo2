package com.cn.socketAndNetty.netty.demo.encoderAndDecoder;

import com.cn.socketAndNetty.netty.demo.Config;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 15:58
 **/
public class DemoServer2 {
    public static void main(String[] args) throws InterruptedException {
        int port = 8008;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //指定连接队列大小
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //KeepAlive
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 配置拆包器，需要在添加Handler前设置
                             */
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                                    Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));
                            /**
                             * 配置编码器与解码器
                             * 配置了编码器与解码器之后，发送消息可以直接通过ctx.writeAndFlush("消息内容" + Config.DATA_PACK_SEPARATOR)的形式发送
                             */
                            socketChannel.pipeline().addLast("encoder", new StringEncoder());
                            socketChannel.pipeline().addLast("decoder", new StringDecoder());
                            /**
                             * 添加handler2
                             */
                            socketChannel.pipeline().addLast(new DemoServerHandler2());
                        }
                    });

            // Start server
            ChannelFuture f = b.bind(port).sync();
            if(f.isSuccess()) {
                System.out.println("Server,启动Netty服务端成功，端口号：" + port);
            }

            // Wait until the server socketAndNetty closed
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }


    }
}
