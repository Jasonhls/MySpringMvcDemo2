package com.cn.socketAndNetty3.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Author: helisen
 * @Date 2021/9/16 11:27
 * @Description:
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //情况1：ByteBuf
        /*if(byteBuf.readableBytes() < 4) {
            return;
        }

        list.add(byteBuf.readBytes(4));*/

        //情况2：
        if(byteBuf.readableBytes() < 4) {
            return;
        }
        list.add(new UnixTime(byteBuf.readUnsignedInt()));
    }
}
