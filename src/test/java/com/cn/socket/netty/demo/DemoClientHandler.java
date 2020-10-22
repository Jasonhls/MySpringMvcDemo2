package com.cn.socket.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.nio.charset.Charset;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 16:31
 **/
public class DemoClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client， channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        if(byteBuf.isReadable()) {
            //把字节缓存
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println("Client，接受到服务端发来的消息：" + new String(bytes, "utf-8"));

            /*ByteBuf bB = Unpooled.copiedBuffer("你好，服务端", Charset.forName("utf-8"));
            ctx.writeAndFlush(bB);*/
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client，exceptionCaught");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client，channelInactive");
    }

    /**
     * 客户端配置心跳包，需要重写userEventTriggered方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;
        System.out.println("Client，Idle：" + event.state());
        switch (event.state()) {
            case READER_IDLE:
                break;
            case WRITER_IDLE:
                /**
                 * 如果是写，响应数据给服务端，告诉服务端，我还活着
                 * 由于配置拆包器，因此发送消息的时候必须在消息尾部增加分隔符
                 */
                ByteBuf byteBuf = Unpooled.copiedBuffer("我是客户端，我还活着，发送心跳^v^v" + Config.DATA_PACK_SEPARATOR, Charset.forName("utf-8"));
                ctx.writeAndFlush(byteBuf);
                break;
            case ALL_IDLE:
                break;
            default:
                super.userEventTriggered(ctx, evt);
                break;
        }
    }
}
