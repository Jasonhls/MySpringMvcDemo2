package com.cn.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

public class ZookeeperDemo {
    private static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .sessionTimeoutMs(50000)
            .connectionTimeoutMs(30000)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    private void createNode() {
        try {
            //创建会话
            client.start();
            //创建一个初始内容为空的节点
            client.create().forPath("/China");
            client.create().forPath("/America", "zhangsan".getBytes());
            //创建一个初始内容为空的临时节点
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/France");
            //递归创建,/Russia是持久节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .forPath("/Russia/car", "haha".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateNode() {
        createNode();
    }

    private void getNode() {
        try {
            client.start();
            byte[] data = client.getData().forPath("/America");
            System.out.println(new String(data));
            //传入一个旧的stat变量，来存储服务端返回的最新的节点状态信息
            byte[] data2 = client.getData().storingStatIn(new Stat()).forPath("/America");
            System.out.println(new String(data2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNode() {
        getNode();
    }

    private void updateData() {
        try {
            client.start();
            //执行setData一次会增加该节点的版本值：dataVersion
//            client.setData().forPath("/America");
            client.setData().withVersion(0).forPath("/America", "lisi".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateNode() {
        updateData();
    }

    public void deleteNode() {
        try {
            client.start();
            //只能删除叶子节点
            client.delete().forPath("/China");
            //删除一个节点，并递归删除其所有的子节点
            client.delete().deletingChildrenIfNeeded().forPath("/Russia");
            //强制指定版本进行删除
            client.delete().withVersion(1).forPath("/America");
            //注意：由于一些网络原因，上述的删除操作有可能失败，使得guranteed()，如果删除失败，会记录下来，
            // 只要会话有效，就会不断的重试，直到删除成功为止，下面的guranteed()方法就是保证删除成功，如果失败会不断重试。
//            client.delete().guaranteed().forPath("/America");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteNode() {
        deleteNode();
    }



    @Test
    public void watch() {
        try {
            client.start();
            client.create().creatingParentsIfNeeded().forPath("/book/computer", "java".getBytes());
            //添加NodeCache
            NodeCache nodeCache = new NodeCache(client, "/book/computer");
            //添加 NodeCache Listener
            nodeCache.getListenable().addListener(() ->
                    System.out.println("新的节点数据：" + new String(nodeCache.getCurrentData().getData())));
            nodeCache.start(true);

            client.setData().forPath("/book/computer", "c++".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void watchChildren() {
        try {
            client.start();
            client.delete().deletingChildrenIfNeeded().forPath("/book13");
            //添加 pathChildrenCache
            PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/book13", true);
            pathChildrenCache.getListenable().addListener((client, event) -> {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("新增子节点" + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("子节点数据变化：" + event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("删除子节点：" + event.getData().getPath());
                        break;
                    default:
                        break;
                }
            });
            pathChildrenCache.start();

            client.create().forPath("/book13");
            client.create().forPath("/book13/car", "bmw".getBytes());
            client.setData().forPath("/book13/car", "hls".getBytes());
            byte[] bytes = client.getData().forPath("/book13/car");
            System.out.println(new String(bytes));
            client.delete().forPath("/book13/car");

            //监听日志打印出来比较慢，所以这里需要等待
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
