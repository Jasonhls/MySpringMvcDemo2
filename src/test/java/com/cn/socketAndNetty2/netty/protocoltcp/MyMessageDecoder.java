package com.cn.socketAndNetty2.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 18:20
 **/
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("MyMessageDecoder decode方法被调用");
        //需要将得到的二进制字节码 -> MessageProtocol
        int length = byteBuf.readInt();
        byte[] content = new byte[length];
        byteBuf.readBytes(content);

        //封装成 MessageProtocol 对象，放入List，传递下一个handler处理
        MessageProtocol mp = new MessageProtocol();
        mp.setLen(length);
        mp.setContent(content);
        list.add(mp);
    }
}
