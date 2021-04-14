package com.cn.socketAndNetty2.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @description: 说明：
 * 1.我们自定义一个handler，需要继承netty 规定好的HandlerAdapter（规范）
 * 2.这时我们自定义一个handler，才能称为一个handler
 * @author: helisen
 * @create: 2021-04-13 14:18
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    //读取客户端发送的消息
    //1.ChannelHandlerContext ctx；上下文对象，含有 管道 pipeline，通道（数据的读写） ，地址
    //2.Object msg；就是客户端发送的数据，默认是Object
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //比如这里我们有一个非常耗时长的业务 -> 异步执行 -> 提交到该channel 对应的
        //NioEventLoop的taskQueue中

        //解决方案1 用户程序自定义的普通任务
        /*ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端 喵2", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //上面的任务是10秒后执行打印的，然后下面第二个任务中等20秒，是在第一个任务的基础上再等20秒，也就是启动后30秒才打印任务2的的日志
        //为什么是这样的，因为是同一个线程去执行上面的任务1和下面的任务2
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端 喵3", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //用户自定义定时任务  --> 该任务是提交到 scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端 喵4", CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5, TimeUnit.SECONDS);


        System.out.println("go on 。。。");*/

        //如果WorkerEventLoopGroup中只配置了两个EventLoop，那么如果启动3个客户端，WorkerEventLoopGroup会轮询的方式分配EventLoop，
        //第一个客户端使用第一个EventLoop，第二个客户端使用第二个EventLoop，第三个客户端还是使用的是第一个EventLoop，只不过每次
        //处理客户端的请求连接，对应的channel是不同的，每次的channel都是不同的。
        System.out.println("服务器读取线程" + Thread.currentThread().getName() + " 对应的channel=" + ctx.channel());
        System.out.println("server ctx = " + ctx);
        System.out.println("看看channel 和 pipeline 的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链接，出站入站
        //将 msg 转成 ByteBuf
        //ByteBuf是 Netty提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + channel.remoteAddress());
    }

    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 将数据写入到缓存，并刷新
        //一般讲，对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端~ 喵1", CharsetUtil.UTF_8));
    }

    //处理异常，一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();//或直接使用ctx.close();
    }
}
