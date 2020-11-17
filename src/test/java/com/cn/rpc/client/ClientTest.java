package com.cn.rpc.client;

import com.cn.rpc.client.proxy.RpcClientProxy;
import com.cn.rpc.client.discovery.IServiceDiscovery;
import com.cn.rpc.client.discovery.ServiceDiscoveryImpl;
import com.cn.rpc.server.IGpHello;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:55
 **/
public class ClientTest {
    public static void main(String[] args) {
        IServiceDiscovery discovery = new ServiceDiscoveryImpl();
        RpcClientProxy proxy = new RpcClientProxy(discovery);
        IGpHello iGpHello = proxy.create(IGpHello.class);
        System.out.println(iGpHello.sayHello("Jack"));
    }
}
