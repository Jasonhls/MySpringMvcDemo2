package com.cn.sockeAndNetty4.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/13:47
 * @Description:
 */
public class AioServer {
    //线程池：用于接受客户端连接到来，这个线程池不负责处理客户端的IO业务(推荐自定义pool)
    //主要作用：处理到来的IO事件和派发CompletionHandler(接受OS的异步回调)
    private ExecutorService servicePool = Executors.newFixedThreadPool(2);
    //异步通道的分组管理，目的是为了资源共享，也承接了之前NIO中的Selector工作。
    private AsynchronousChannelGroup group;
    //异步的服务端通道，类似于NIO中的ServerSocketChannel
    private AsynchronousServerSocketChannel serverChannel;

    //AIO服务端的构造方法：创建AIO服务端
    public AioServer(String ip, int port) {
        //使用线程组，绑定线程池，通过多线程技术监听客户端连接
        try {
            group = AsynchronousChannelGroup.withThreadPool(servicePool);
            //创建AIO服务端通道，并通过线程组对到来的客户端连接进行管理
            serverChannel = AsynchronousServerSocketChannel.open();
            //为服务端通道绑定IP地址与端口
            serverChannel.bind(new InetSocketAddress(ip, port));
            System.out.println(">>>>>...AIO服务端启动...>>>>>");
            /**
             * 第一个参数：作为处理器的附加参数
             * 第二个参数：注册一个提供给OS回调的处理器
             */
            serverChannel.accept(this, new AioHandler());
            /**
             * 这里朱啊哟是为了阻塞住主线程退出，确保服务端的正常运行。
             * （与CompletableFuture相同，主线程退出后就无法获取回调）
             */
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //关闭服务端的方法
    public void serverDown() {
        try {
            serverChannel.close();
            group.shutdown();
            servicePool.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //获取服务端通道的方法
    public AsynchronousServerSocketChannel getServerChannel() {
        return this.serverChannel;
    }

    public static void main(String[] args) {
        //创建一个AIO的服务端
        AioServer server = new AioServer("127.0.0.1", 8888);
        //关闭AIO服务端
        server.serverDown();
    }
}
