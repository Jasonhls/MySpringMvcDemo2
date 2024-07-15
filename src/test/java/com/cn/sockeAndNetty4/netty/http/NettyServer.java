package com.cn.sockeAndNetty4.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 何立森
 * @Date: 2024/07/12/16:06
 * @Description:
 */
public class NettyServer {

    private final static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    public static void main(String[] args) {
        String backPackages = "com.cn.sockeAndNetty4.netty.http";
        //初始化http处理容器
        new HttpContainer(new String[]{backPackages}).init();
        //启动nettyServer
        new NettyServer().bind(7397);

    }

    public void bind(int port) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new MyChannelInitializer());
            ChannelFuture future = b.bind(port).sync();
            logger.info("服务器启动成功！");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            childGroup.shutdownGracefully();
            parentGroup.shutdownGracefully();
        }
    }
}
