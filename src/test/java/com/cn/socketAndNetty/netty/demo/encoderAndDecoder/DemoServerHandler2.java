package com.cn.socketAndNetty.netty.demo.encoderAndDecoder;

import com.cn.socketAndNetty.netty.demo.Config;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 15:59
 **/
public class DemoServerHandler2 extends ChannelInboundHandlerAdapter {

    /**
     * 服务端连接客户端的时候会调用这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /*System.out.println("有新的客户端连接服务端了");
        ByteBuf byteBuf = ctx.alloc().buffer(8);
        byteBuf.writeBytes("你好，客户端".getBytes("GBK"));
        //响应数据给客户端
        ctx.writeAndFlush(byteBuf);*/
        System.out.println("Server，channelActive");
        for (int i = 0; i < 100; i++) {
            /**
             * 为了防止粘包和拆包，在服务端和客户端都配置拆包器，同时发送消息的时候，在发送的数据的末尾增加分隔符
             由于配置了编码器和解码器，直接可以发送String类型的消息，不需要借助于ByteBuf了
             */
            ctx.writeAndFlush("你好，客户端" + Config.DATA_PACK_SEPARATOR);
        }
    }

    /**
     * 处理客户端发来的请求数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf byteBuf = (ByteBuf) msg;
        try {
            /*byte[] bytes = new byte[byteBuf.readableBytes()];
            if(byteBuf.isReadable()) {
                byteBuf.readBytes(bytes);
                System.out.println("收到客户端发来的消息：" + new String(bytes));*/


                /**
                 * 由于配置了编码器和解码器，直接可以发送String类型的消息，不需要借助于ByteBuf了
                 * 同时可以直接获取消息，因为消息已经解码好了
                */
                System.out.println("收到客户端发来的消息：" + msg);
                ctx.writeAndFlush("处理完客户端的请求，返回给客户端" + Config.DATA_PACK_SEPARATOR);
//            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Server，exceptionCaught");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server，channelInactive");
    }
}
