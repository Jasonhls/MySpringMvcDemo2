package com.cn.socketAndNetty2.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-13 14:06
 **/
public class NettyServer {
    public static void main(String[] args) throws Exception{
        //创建BossGroup 和 WorkerGroup
        //说明：
        //1.创建两个线程组 bossGroup和 workerGroup
        //2.bossGroup 只是处理连接请求，真正和客户端业务处理，会交给workGroup完成
        //3.两个都是无限循环
        //4.bossGroup 和 workerGroup 含有的子线程（NioEventLoop的个数
        //默认实际 cpu核数 * 2，比如我的cpu核数为6，因此bossGroup中有个属性children（类型为EventExecutor数组），里面放了12个子线程，子线程类型为NioEventLoop。
        //比如服务器端有12个NioEventLoop，那么第一个客户端连接，用NioEventLoop1，第二个客户端连接，用NioEventLoop2 。。。，第12个客户端连接，用NioEventLoop12，
        // 那么第13个客户端来连接，就还是用NioEventLoop1，就这样循环。
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();//NioEventLoopGroup的构造方法可以设置个数，设置几个，属性children中就包含了几个NioEventLoop。
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程来进行设置
            bootstrap.group(bossGroup, workGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)  //设置保持活动连接状态
                    .handler(null)   //该handler对应bossGroup，childHandler对应workerGroup
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道测试对象（匿名对象）
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            System.out.println("客户socketChannel hashcode=" + socketChannel.hashCode());
                            //可以使用一个集合管理 SocketChannel，在推送消息时，可以将业务加入到各个channel对应的 NioEventLoop 的 taskQueue 或者 ScheduleTaskQueue
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });  //给我们的workGroup的EventLoop对应的管道设置处理器

            System.out.println("。。。。服务器 is ready。。。");
            //绑定一个端口并且同步，生成一个ChannelFuture对象
            //启动服务器（并绑定端口）
            ChannelFuture channelFuture = bootstrap.bind(6668).sync();

            //给ChannelFuture注册监听器，监控我们关心的事件
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    }else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });


            //对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
