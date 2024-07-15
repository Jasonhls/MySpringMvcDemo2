package com.cn.sockeAndNetty4.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: 何立森
 * @Date: 2024/07/12/15:54
 * @Description:
 */
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //http 编解码
        pipeline.addLast("http编解码器", new HttpServerCodec());
        pipeline.addLast("http报文聚合器", new HttpObjectAggregator(64 * 1024));
        //在管道中添加我们自己的接受数据实现方法
        pipeline.addLast("自定义请求处理器", new MyServerHandler());
    }
}
