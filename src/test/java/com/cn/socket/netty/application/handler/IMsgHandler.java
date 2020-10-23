package com.cn.socket.netty.application.handler;

import com.cn.socket.netty.application.wrapper.ChannelHandlerContextWrapper;
import com.cn.socket.netty.application.Msg;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 16:25
 **/
public interface IMsgHandler {
    /**
     * 服务端处理消息
     * @param wrapper
     * @param msg
     */
    void serverHandle(ChannelHandlerContextWrapper wrapper, Msg msg);

    /**
     * 客户端处理消息
     * @param msg
     */
    void clientHandle(Msg msg);
}
