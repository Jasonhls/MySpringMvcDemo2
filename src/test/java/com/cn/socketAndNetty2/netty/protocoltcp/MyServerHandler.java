package com.cn.socketAndNetty2.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 17:58
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol messageProtocol) throws Exception {
        //接受到数据，并处理
        int length = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();

        System.out.println("服务端接受到信息如下");
        System.out.println("长度=" + length);
        System.out.println("内容=" + new String(content, CharsetUtil.UTF_8));

        System.out.println("服务器接受到消息包数量=" + (++this.count));
        System.out.println("-------------------------------------------");

        //回复消息
        String responseContent = UUID.randomUUID().toString();
        int responseLength = responseContent.getBytes(CharsetUtil.UTF_8).length;
        byte[] responseContent2 = responseContent.getBytes(CharsetUtil.UTF_8);
        MessageProtocol mp = new MessageProtocol();
        mp.setLen(responseLength);
        mp.setContent(responseContent2);

        ctx.writeAndFlush(mp);//给客户端回复消息，消息会先进行编码，然后再发送字节码，到了客户端消息会先被解码，然后再执行客户端的业务handler
        System.out.println("================================================");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
