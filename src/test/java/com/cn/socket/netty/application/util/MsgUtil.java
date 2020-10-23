package com.cn.socket.netty.application.util;

import com.cn.socket.netty.application.wrapper.ChannelHandlerContextWrapper;
import com.cn.socket.netty.application.Msg;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:44
 **/
public class MsgUtil {
    /**
     * 发送欢迎消息
     * @param wrapper
     */
    public static void sendWelcomeMsg(ChannelHandlerContextWrapper wrapper) {
        Msg msg = new Msg();
        msg.type = Msg.TYPE_WELCOME;
        msg.msg = "你好，客户端";
        wrapper.writeAndFlush(msg);
    }

    /**
     * 发送消息
     * @param wrapper
     * @param msg
     */
    public static void sendMsg(ChannelHandlerContextWrapper wrapper, Msg msg) {
        wrapper.writeAndFlush(msg);
    }
}
