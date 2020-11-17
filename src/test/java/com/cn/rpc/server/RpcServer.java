package com.cn.rpc.server;

import com.cn.rpc.server.registry.IRegisterCenter;
import com.cn.rpc.annotation.RpcAnnotation;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:35
 **/
public class RpcServer {
    private Map<String, Object> handlerMap = new HashMap<>();

    private String serviceAddress;
    private IRegisterCenter registerCenter;

    public RpcServer(IRegisterCenter registerCenter, String serviceAddress) {
        this.registerCenter = registerCenter;
        this.serviceAddress = serviceAddress;
    }

    public void publisher() {
        for (String serviceName : handlerMap.keySet()) {
            registerCenter.register(serviceName, serviceAddress);
        }
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);

            bootstrap.childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                            0, 4, 0,4));
                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                    pipeline.addLast("encoder", new ObjectEncoder());
                    pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                    pipeline.addLast(new RpcServerHandler(handlerMap));
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            String[] addressArr = serviceAddress.split(":");
            String ip = addressArr[0];
            int port = Integer.parseInt(addressArr[1]);
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            System.out.println("netty服务端启动成功，等待客户端的连接");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void bind(Object... services) {
        for (Object service : services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();
            handlerMap.put(serviceName, service);
        }
    }
}
