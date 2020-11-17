package com.cn.rpc.client.discovery;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:22
 **/
public interface IServiceDiscovery {
    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    String discover(String serviceName);
}
