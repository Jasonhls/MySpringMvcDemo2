package com.cn.socketAndNetty.netty.demo2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-15 10:17
 **/
public class Client {
    private EventLoopGroup eventLoopGroup;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;

    public Client() {
        this.eventLoopGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap();
    }

    public void start(int port) {
        try {
            this.bootstrap.group(this.eventLoopGroup)
                    //注意这里必须是NioSocketChannel.class，不能是NioServerSocketChannel.class（专门给server用的）
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new HelloClientHandler());
                        }
                    });
            this.channelFuture = this.bootstrap.connect("127.0.0.1", port).sync();
            this.channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start(8008);
    }
}
