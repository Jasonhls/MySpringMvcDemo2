package com.cn.socketAndNetty2.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 17:58
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        //接受到数据，并处理
        int length = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();

        System.out.println("服务端接受到信息如下");
        System.out.println("长度=" + length);
        System.out.println("内容=" + new String(content, CharsetUtil.UTF_8));

        System.out.println("服务器接受到消息包数量=" + (++this.count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
