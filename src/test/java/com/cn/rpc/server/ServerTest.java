package com.cn.rpc.server;

import com.cn.rpc.server.registry.IRegisterCenter;
import com.cn.rpc.server.registry.RegisterCenterImpl;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:34
 **/
public class ServerTest {
    public static void main(String[] args) {
        IGpHello iGpHello = new GpHelloImpl();
        IRegisterCenter registerCenter = new RegisterCenterImpl();
        RpcServer rpcServer = new RpcServer(registerCenter, "127.0.0.1:8080");
        rpcServer.bind(iGpHello);
        rpcServer.publisher();
    }
}
