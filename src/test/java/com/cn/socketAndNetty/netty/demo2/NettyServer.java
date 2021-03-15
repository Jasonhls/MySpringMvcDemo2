package com.cn.socketAndNetty.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-15 09:56
 **/
public class NettyServer {
    private EventLoopGroup worker;
    private EventLoopGroup boss;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    public NettyServer() {
        this.worker = new NioEventLoopGroup();
        this.boss = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
    }

    public void start(int port) {
        try {
            this.serverBootstrap.group(this.boss, this.worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //编码器，将字符串消息转换为字节发送给客户端
                            socketChannel.pipeline().addLast(new StringEncoder());
                            //解码器，将收到的字节消息转换为字符串交给下一个处理器
                            socketChannel.pipeline().addLast(new StringDecoder());
                            //业务处理器
                            socketChannel.pipeline().addLast(new HelloServerHandler());
                        }
                    });
            this.channelFuture = this.serverBootstrap.bind(port).sync();
            System.out.println("netty服务器启动");
            this.channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.worker.shutdownGracefully();
            this.boss.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.start(8008);
    }
}
