package com.cn.sockeAndNetty4.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/15:20
 * @Description:
 */
public class SimpleNettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //读取客户端消息：msg --> ByteBuf --> byte[] --> String
        System.out.println("SimpleNettyServerHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] byteMsg = new byte[result.readableBytes()];
        //msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(byteMsg);
        String resultStr = new String(byteMsg);
        //接受并打印客户端的信息
        System.out.println("Client said:" + resultStr);
        //释放资源，这行很关键
        result.release();

        //向客户端发送消息：msg --> byte[] --> ByteBuf
        String response = "hello client";
        //在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf buf = ctx.alloc().buffer(4 * response.length());
        buf.writeBytes(response.getBytes());
        ctx.write(buf);
        ctx.flush();
    }

    /**
     * 出现异常的操作
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 信息获取完毕后操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
