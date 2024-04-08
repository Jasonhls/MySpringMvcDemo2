package com.cn.sockeAndNetty4.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/13:56
 * @Description:
 */
public class AioHandler implements CompletionHandler<AsynchronousSocketChannel, AioServer> {

    //负责具体IO业务处理的线程池
    private ExecutorService ioDisposePool = Executors.newFixedThreadPool(2);

    @Override
    public void completed(AsynchronousSocketChannel client, AioServer server) {
        /**
         * 调用监听方法继续监听其他客户端连接
         * 这里不会由于递归调用导致堆栈溢出
         * 因为发起accept监听的线程和IO回调的线程并非同一个
         */
        server.getServerChannel().accept(server, this);
        //将接下来的IO数据处理业务丢给线程池ioDisposePool处理
        ioDisposePool.execute(() -> {
            //创建一个字节缓冲区，用于接受数据
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            /**
             * 第一个参数：客户端数据的中转缓冲区（分散读取时使用）
             * 第二个参数：存放OS处理好的客户端数据缓冲区（OS会主动将数据放进来）
             * 第三个参数：对于IO数据的具体业务处理
             */
            client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                /**
                 * 第一个参数：读取到的客户端IO数据的长度
                 * 第二个参数：存放IO数据的缓冲区（对应上述read()方法的第二个参数）
                 */
                @Override
                public void completed(Integer length, ByteBuffer buffer) {
                    //length代表数据的字节数，不为-1代表通道未关闭
                    if(length != -1) {
                        //将缓冲区转换为读取模式
                        buffer.flip();
                        //输出接受到的客户端数据
                        System.out.println("服务端收到信息：" + new String(buffer.array(), 0, buffer.remaining()));
                        //将处理完后的缓冲区清空
                        buffer.clear();

                        //向客户端写回数据
                        String msg = "我是服务端-竹子";
                        buffer.put(msg.getBytes());
                        buffer.flip();
                        client.write(buffer);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
        });
    }

    /**
     * 操作系统处理IO数据时，出现异常的回调函数
     */
    @Override
    public void failed(Throwable exc, AioServer attachment) {
        //打印异常的堆栈信息
        exc.printStackTrace();
    }
}
