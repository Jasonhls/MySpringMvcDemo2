package com.cn.socketAndNetty.netty.application.connection;

import com.cn.socketAndNetty.netty.application.callback.IConnectionCallback;
import com.cn.socketAndNetty.netty.application.callback.IMsgCallback;
import com.cn.socketAndNetty.netty.application.Msg;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:29
 **/
public class ConnectionManager implements IConnection {

    private static IConnection iConnection;

    public ConnectionManager() {
    }

    static class ConnectionManagerInner {
        private static ConnectionManager INSTANCE = new ConnectionManager();
    }

    public static ConnectionManager getInstance() {
        return ConnectionManagerInner.INSTANCE;
    }

    public static void initConnection(IConnection connection) {
        iConnection = connection;
    }

    private void checkInit() {
        if(iConnection == null) {
            throw new IllegalAccessError("please invoke initConnection first");
        }
    }

    @Override
    public void connect(String host, int port, IConnectionCallback callback) {
        checkInit();
        iConnection.connect(host, port, callback);
    }

    public void sendMsg(Msg msg) {
        checkInit();
        iConnection.sendMsg(msg);
    }

    @Override
    public void registerMsgCallback(IMsgCallback callback) {
        iConnection.registerMsgCallback(callback);
    }
}
