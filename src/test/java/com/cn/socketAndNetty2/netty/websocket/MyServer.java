package com.cn.socketAndNetty2.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 14:00
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
                            //因为基于http协议，使用http的编码和解码器
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            //是以块方式写，添加ChunkedWrite处理器
                            pipeline.addLast(new ChunkedWriteHandler());

                            /**
                             * 说明：
                             * 1、http数据在传输过程中是分段的，HttpObjectAggregator，它可以将多个段聚合起来
                             * 2、这就是为什么当浏览器发送大量数据时，就会发出多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            /**
                             * 说明：
                             * 1、对于websocket，它的数据是以 帧（frame）形式传递
                             * 2、可以看到ebSocketFrame 下面有六个子类
                             * 3、浏览器请求时 ws://localhost:7000/hello 表示请求的uri
                             * 4、WebSocketServerProtocolHandler 核心功能是将http协议升级为 ws 协议，保持长连接
                             * 5、是通过一个 状态码 101
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));//这里的websocketPath需要与客户端的请求地址对应上

                            //自定义的handler，处理业务逻辑
                            pipeline.addLast(new MyTestWebSocketFrameHandler());
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
