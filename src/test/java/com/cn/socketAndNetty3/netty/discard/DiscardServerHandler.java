package com.cn.socketAndNetty3.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author: helisen
 * @Date 2021/9/16 10:25
 * @Description:
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        //情况1：不做任何处理
        //discard the received data silently
//        ((ByteBuf)msg).release();

        //情况2：打印接受到的内容
        /*ByteBuf in = (ByteBuf)msg;
        try {
            while(in.isReadable()) {
                System.out.println((char)in.readByte());
                System.out.flush();
            }
        }finally {
            ReferenceCountUtil.release(msg);
        }*/

        //情况3：接受到内容，就把内容响应回去
        ctx.write(msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
