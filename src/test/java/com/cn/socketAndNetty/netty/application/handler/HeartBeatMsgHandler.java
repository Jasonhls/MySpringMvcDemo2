package com.cn.socketAndNetty.netty.application.handler;

import com.cn.socketAndNetty.netty.application.Msg;
import com.cn.socketAndNetty.netty.application.wrapper.ChannelHandlerContextWrapper;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-23 15:47
 **/
public class HeartBeatMsgHandler extends BaseCommandHandler{
    @Override
    public void doClientHandle(Msg msg) {
        System.out.println("client handle heartbeat msg：" + msg.toString());
    }

    @Override
    public void serverHandle(ChannelHandlerContextWrapper wrapper, Msg msg) {
        System.out.println("server handle heartbeat msg：" + msg.toString());
    }
}
