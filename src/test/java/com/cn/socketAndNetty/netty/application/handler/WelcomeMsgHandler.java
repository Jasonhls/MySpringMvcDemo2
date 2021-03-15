package com.cn.socketAndNetty.netty.application.handler;

import com.cn.socketAndNetty.netty.application.wrapper.ChannelHandlerContextWrapper;
import com.cn.socketAndNetty.netty.application.Msg;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-23 14:48
 **/
public class WelcomeMsgHandler extends BaseCommandHandler{
    @Override
    public void doClientHandle(Msg msg) {
        System.out.println("client handle welcome msg：" + msg.toString());
    }

    @Override
    public void serverHandle(ChannelHandlerContextWrapper wrapper, Msg msg) {
        System.out.println("server handle welcome msg：" + msg.toString());
    }
}
