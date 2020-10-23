package com.cn.socket.netty.application.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.socket.netty.application.wrapper.ChannelHandlerContextWrapper;
import com.cn.socket.netty.application.Msg;
import com.cn.socket.netty.application.util.MsgUtil;
import com.cn.socket.netty.application.dispatcher.MsgDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 15:59
 **/
public class NettyMyServerHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContextWrapper wrapper;

    /**
     * 服务端连接客户端的时候会调用这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server，channelActive");
        wrapper = new ChannelHandlerContextWrapper(ctx);
        MsgUtil.sendWelcomeMsg(wrapper);
    }

    /**
     * 处理客户端发来的请求数据
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
                /**
                 * 由于配置了编码器和解码器，直接可以发送String类型的消息，不需要借助于ByteBuf了
                 * 同时可以直接获取消息，因为消息已经解码好了
                */
                System.out.println("收到客户端发来的消息：" + msg);
                /**
                 * 收到客户端的请求，先执行分发器逻辑，响应给客户端
                 */
            Msg m = JSON.parseObject((String) msg, Msg.class);
            MsgDispatcher.serverDispatcher(wrapper, m);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Server，exceptionCaught");
        cause.printStackTrace();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server，channelInactive");
    }
}
