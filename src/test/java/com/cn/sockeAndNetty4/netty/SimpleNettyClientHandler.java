package com.cn.sockeAndNetty4.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/15:40
 * @Description:
 */
public class SimpleNettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 接受服务端的消息
     * @param ctx
     * @param msg
     * @throws Exception
     * msg --> ByteBuf --> byte[] --> String
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("SimpleClientHandler.channelRead");
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("Server said:" + new String(bytes));
        buf.release();
    }

    /**
     * 向服务端发送消息
     * @param ctx
     * @throws Exception
     * msg --> byte[] --> ByteBuf
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg = "hello Server!";
        ByteBuf buf = ctx.alloc().buffer(4 * msg.length());
        buf.writeBytes(msg.getBytes());
        ctx.write(buf);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //出现异常关闭连接
        ctx.close();
    }
}
