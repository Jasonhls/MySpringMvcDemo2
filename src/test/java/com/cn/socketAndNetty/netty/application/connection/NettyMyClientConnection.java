package com.cn.socketAndNetty.netty.application.connection;

import com.cn.socketAndNetty.netty.application.callback.IConnectionCallback;
import com.cn.socketAndNetty.netty.application.callback.IMsgCallback;
import com.cn.socketAndNetty.netty.application.Msg;
import com.cn.socketAndNetty.netty.application.client.NettyMyClient;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:19
 **/
public class NettyMyClientConnection implements IConnection {

    private NettyMyClient nettyMyClient;

    @Override
    public void connect(String host, int port, IConnectionCallback callback) {
        if(nettyMyClient == null) {
            nettyMyClient = new NettyMyClient(host, port);
            nettyMyClient.setCallback(callback);
            nettyMyClient.connection();
        }
    }

    @Override
    public void sendMsg(Msg msg) {
        if(nettyMyClient == null) {
            throw new IllegalAccessError("please invoke connect first");
        }
        nettyMyClient.sendMsg(msg);
    }

    @Override
    public void registerMsgCallback(IMsgCallback callback) {
        if(nettyMyClient == null) {
            throw new IllegalAccessError("please invoke connect first");
        }
        nettyMyClient.registerMsgCallback(callback);
    }
}
