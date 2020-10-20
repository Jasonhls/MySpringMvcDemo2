package com.cn.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-19 10:06
 **/
public class NioClient {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8379);
        SocketChannel sc = null;
        try {
            /**
             * 1.获取通道
             * 这行代码执行后，会发送客户端连接信息给到服务器端，
             */
            sc = SocketChannel.open(address);
            /**
             * 2.切换成非阻塞模式
             */
            sc.configureBlocking(false);
            /**
             *3.分配指定大小的缓冲区
             */
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(true) {
                byte[] bytes = new byte[1024];
                System.out.println("请输入数据：");
                System.in.read(bytes);//阻塞在这行代码这里，输入了值之后按enter，才会继续执行下面的代码
                System.out.println("-----输入数据之后-------");
                //将数据放到缓存中
                buffer.put(bytes);
                //切换数据读取模式
                buffer.flip();
                //将缓存中的数据写入到通道中
                sc.write(buffer);
                //清空缓存
                buffer.clear();
                /**
                 * 异步处理服务端响应回来的数据
                 */
                executorService.execute(new DealWithResp(readBuffer, sc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(sc != null) {
                try {
                    sc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class DealWithResp implements Runnable{
        private ByteBuffer buffer;
        private SocketChannel sc;

        public DealWithResp(ByteBuffer buffer, SocketChannel sc) {
            this.buffer = buffer;
            this.sc = sc;
        }

        @Override
        public void run() {
            boolean flag = false;
            while(!flag) {
                try {
                    int len;
                    while((len = sc.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println("服务端响应回来的数据：" + new String(buffer.array(), 0, len));
                        buffer.clear();
                        flag = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
