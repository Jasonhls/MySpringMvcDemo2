package com.cn.socket.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 10:38
 **/
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当产生一个连接并且准备就绪的时候，会执行channelActive方法，即有客户端连接的时候，就会调用下面的这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        //分配四个字节的ByteBuf
        final ByteBuf byteBuf = ctx.alloc().buffer(4);
        //把时间值写入缓存ByteBuf中
        byteBuf.writeInt((int)(System.currentTimeMillis() / 1000L + 2208988800L));
        /**
         * 将缓存ByteBuf中的值写入通道中，这里没有使用flip()方法（一般NIO的操作中，发送数据之前（即将缓存中的数据写入通道之前），
         * 需要调用flip()方法，具体可以去参考NIO的写法），因为ByteBuf（不同于ByteBuffer）有两个指针，一个指针专门用于读操作，一个指针专门用于写操作，
         * 它们之间互不影响
         */
        final ChannelFuture f = ctx.writeAndFlush(byteBuf);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                assert f == channelFuture;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
