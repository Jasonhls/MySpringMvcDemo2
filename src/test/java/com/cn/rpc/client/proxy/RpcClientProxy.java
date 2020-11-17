package com.cn.rpc.client.proxy;

import com.cn.rpc.bean.RpcRequest;
import com.cn.rpc.client.discovery.IServiceDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 10:09
 **/
public class RpcClientProxy {
    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T create(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    RpcRequest request = new RpcRequest();
                    request.setClassName(method.getDeclaringClass().getName());
                    request.setMethodName(method.getName());
                    request.setTypes(method.getParameterTypes());
                    request.setParams(args);

                    String serviceName = interfaceClass.getName();
                    String serviceAddress = serviceDiscovery.discover(serviceName);

                    String[] arrs = serviceAddress.split(":");
                    String host = arrs[0];
                    Integer port = Integer.parseInt(arrs[1]);

                    final RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();

                    EventLoopGroup group = new NioEventLoopGroup();
                    try {
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(group).channel(NioSocketChannel.class)
                                .option(ChannelOption.TCP_NODELAY, true)
                                .handler(new ChannelInitializer<SocketChannel>() {
                                    @Override
                                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                                        ChannelPipeline pipeline = socketChannel.pipeline();
                                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                                0, 4, 0,4));
                                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                        pipeline.addLast("encoder", new ObjectEncoder());
                                        pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
                                        pipeline.addLast(rpcProxyHandler);
                                    }
                                });
                        ChannelFuture future = bootstrap.connect(host, port).sync();
                        future.channel().writeAndFlush(request);
                        future.channel().closeFuture().sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        group.shutdownGracefully();
                    }
                    return rpcProxyHandler.getResponse();
                }
            });
    }
}
