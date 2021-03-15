package com.cn.socketAndNetty.netty.application.handler;

import com.cn.socketAndNetty.netty.application.Msg;
import com.cn.socketAndNetty.netty.application.MsgQueue;
import com.cn.socketAndNetty.netty.application.dispatcher.MsgDispatcher;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 16:32
 **/
public abstract class BaseCommandHandler implements IMsgHandler{

    @Override
    public void clientHandle(Msg msg) {
        executeClientHandle(msg);
    }

    public void executeClientHandle(Msg msg) {
        System.out.println("Client，received command：" + msg);
        doClientHandle(msg);
        MsgQueue.getInstance().makeUse(false);
        System.out.println("Client，report command：" + msg);
        MsgDispatcher.clientDispatch();
    }

    public abstract void doClientHandle(Msg msg);
}
