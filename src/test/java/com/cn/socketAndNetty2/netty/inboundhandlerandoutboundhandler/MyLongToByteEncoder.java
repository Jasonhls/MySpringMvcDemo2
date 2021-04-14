package com.cn.socketAndNetty2.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 16:24
 **/
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf byteBuf) throws Exception {
        System.out.println("MyLongToByteEncoder encode编码方法被调用");
        System.out.println("msg=" + msg);
        byteBuf.writeLong(msg);
    }
}
