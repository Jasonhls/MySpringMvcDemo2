package com.cn.socketAndNetty.netty.application.connection;

import com.cn.socketAndNetty.netty.application.callback.IConnectionCallback;
import com.cn.socketAndNetty.netty.application.callback.IMsgCallback;
import com.cn.socketAndNetty.netty.application.Msg;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:17
 **/
public interface IConnection {
    /**
     * 连接服务器
     * @param host         服务器地址
     * @param port         端口
     * @param callback  连接回调
     */
    void connect(String host, int port, IConnectionCallback callback);

    /**
     * 发送消息
     * @param msg
     */
    void sendMsg(Msg msg);

    /**
     * 注册消息回调
     * @param callback
     */
    void registerMsgCallback(IMsgCallback callback);
}
