package com.cn.socketAndNetty2.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @description: 说明：
 * 1.SimpleChannelInboundHandler是ChannelInboundHandlerAdapter子类
 * 2.HttpObject 客户端和服务端相互通讯的数据被封装成 HttpObject
 * @author: helisen
 * @create: 2021-04-13 18:13
 **/
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //当有读取事件的时候会触发这个方法
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject) throws Exception {
        //判断 msg 是不是 httpRequest 请求
        if(httpObject instanceof HttpRequest) {
            //每一次客户端的请求，pipeline都是不同的，pipeline里面的TestHttpServerHandler也是不同的。
            System.out.println("pipeline hashcode=" + channelHandlerContext.pipeline().hashCode() +
                    " TestHttpServerHandler hash=" + this.hashCode());
            System.out.println("httpObject 类型=" + httpObject.getClass());
            System.out.println("客户端地址" + channelHandlerContext.channel().remoteAddress());

            //获取到HttpRequest对象
            HttpRequest httpRequest = (HttpRequest) httpObject;
            //获取uri，过滤指定的资源
            URI uri = new URI(httpRequest.getUri());
            if("/favicon.ico".equals(uri.getPath())) {
                System.out.println("请求了/favicon.ico，不做响应");
                return;
            }

            //回复信息给浏览器[http协议]
            ByteBuf content = Unpooled.copiedBuffer("hello，我是服务器", CharsetUtil.UTF_8);
            //构造一个http的响应，即HttpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());//返回长度

            //将构建好的 response 返回
            channelHandlerContext.writeAndFlush(response);

        }
    }
}
