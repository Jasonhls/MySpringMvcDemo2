package com.cn.rpc.server.registry;

import com.cn.rpc.config.ZkConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:12
 **/
public class RegisterCenterImpl implements IRegisterCenter{

    private CuratorFramework  curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    /**
     * 注册服务
     * @param serviceName
     * @param serviceAddress
     */
    @Override
    public void register(String serviceName, String serviceAddress) {
        // 比如 /hls/com.cn.rpc
        String servicePath = ZkConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            if(curatorFramework.checkExists().forPath(servicePath) == null) {
                curatorFramework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(servicePath, "0".getBytes());
            }
            //比如 /hls/
            String addressPath = servicePath + "/" + serviceAddress;
            //创建临时结点，该节点的数据为字符串"0"
            String rsNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                    .forPath(addressPath, "0".getBytes());
            System.out.println("服务注册成功：" + rsNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
