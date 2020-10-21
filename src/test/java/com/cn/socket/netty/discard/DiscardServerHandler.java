package com.cn.socket.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 09:47
 **/
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 有数据过来，就会执行下面的方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /*
        // Discard the received data silently
        ((ByteBuf)msg).release();
        */
        /**
         * 下面这是接受请求过来的数据，并打印出来
         */
        ByteBuf in = (ByteBuf) msg;
        try {
            while(in.isReadable()) {
                System.out.println((char)in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised
        cause.printStackTrace();
        ctx.close();
    }
}
