package com.cn.socketAndNetty2.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 16:13
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //入站时的handler进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());
        //添加出站的编码器
        pipeline.addLast(new MyLongToByteEncoder());
        //自定义的handler处理业务逻辑
        pipeline.addLast(new MyServerHandler());
    }
}
