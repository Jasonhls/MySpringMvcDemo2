package com.cn.rpc.server.registry;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:11
 **/
public interface IRegisterCenter {
    /**
     * 注册服务
     * @param serviceName
     * @param serviceAddress
     */
    void register(String serviceName, String serviceAddress);
}
