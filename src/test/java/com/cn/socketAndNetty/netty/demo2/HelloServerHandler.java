package com.cn.socketAndNetty.netty.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-15 10:13
 **/
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("收到客户端消息：" + s);
        channelHandlerContext.writeAndFlush(s);
    }


}
