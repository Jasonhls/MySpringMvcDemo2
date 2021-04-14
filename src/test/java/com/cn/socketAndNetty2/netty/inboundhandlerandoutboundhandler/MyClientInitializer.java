package com.cn.socketAndNetty2.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 16:22
 **/
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
        //加入一个出站的handler，对数据进行编码
        pipeline.addLast(new MyLongToByteEncoder());
        //这是个入站的解码器
        pipeline.addLast(new MyByteToLongDecoder());
        //再加入自定义的handler，处理业务逻辑
        pipeline.addLast(new MyClientHandler());
    }
}
