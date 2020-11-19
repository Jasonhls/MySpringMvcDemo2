package com.cn.rpc.client.discovery;

import com.cn.rpc.config.ZkConfig;
import com.cn.rpc.loadbalance.LoadBalance;
import com.cn.rpc.loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:22
 **/
public class ServiceDiscoveryImpl implements IServiceDiscovery{

    List<String> repos = new ArrayList<>();

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl() {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR).sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 10))
                .build();
        curatorFramework.start();
    }

    /**
     * 服务发现
     * @param serviceName
     * @return
     */
    @Override
    public String discover(String serviceName) {
        String path = ZkConfig.ZK_REGISTER_PATH + "/" + serviceName;
        try {
            //获取path下所有的子结点集合
            repos = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        registerWatch(path);

        LoadBalance loadBalance = new RandomLoadBalance();
        return loadBalance.select(repos);
    }

    /**
     * 注册监听器
     * @param path
     */
    private void registerWatch(final String path) {
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path ,true);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        childrenCache.getListenable().addListener(listener);
        try {
            childrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("注册PathChild Watcher 异常" + e);
        }
    }
}
