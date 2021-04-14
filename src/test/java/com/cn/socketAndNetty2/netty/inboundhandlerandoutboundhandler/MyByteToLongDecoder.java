package com.cn.socketAndNetty2.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 16:15
 **/
public class MyByteToLongDecoder extends ByteToMessageDecoder {
    /**
     * decode 会根据接收到的数据，被调用多次，直到确定没有新的元素被添加到List，
     * 或者ByteBuf没有更多的可读字节为止，
     * 如果List<Object> list 不为空，就会将list的内容传递给下一个ChannelInboundHandler处理，
     * 该处理器的方法也会被调用多次。
     * @param channelHandlerContext 上下文
     * @param byteBuf  入站的ByteBuf
     * @param list 集合，将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyByteToLongDecoder decode解码方法被调用");
        if(byteBuf.readableBytes() >= 8) {
            //因为Long为8个字节，必须有8个字节，才能读取一个Long
            list.add(byteBuf.readLong());
        }
    }
}
