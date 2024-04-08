package com.cn.sockeAndNetty4.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/14:12
 * @Description:
 */
public class AioClient {
    //客户端的Socket异步通道
    private AsynchronousSocketChannel channel;

    //客户端的构造方法，创建一个AIO客户端
    public AioClient(String ip, int port) {
        try {
            channel = AsynchronousSocketChannel.open();
            //与指定的IP、端口号建立通道连接（阻塞等待连接完成后再操作）
            //如果不加.get()，同时启动多个客户端会抛出如下异常信息：java.nio.channels.NotYetConnectedException
            //这里由于建立连接也是异步的，所以未建立连接直接通信会报错
            channel.connect(new InetSocketAddress(ip, port)).get();
            System.out.println(">>>>>...AIO客户端启动...>>>>>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //客户端向通道中写入数据(往服务端发送数据)的方法
    public void clientWrite(String msg) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(msg.getBytes());
        buffer.flip();
        this.channel.write(buffer);
    }

    //客户端从通道中读取数据(接受服务端数据)的方法
    public void clientRead() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //阻塞读取服务端传输的数据
            this.channel.read(buffer).get();
            buffer.flip();
            System.out.println("客户端收到信息：" + new String(buffer.array(), 0, buffer.remaining()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //关闭客户端通道连接的方法
    public void clientDown() {
        try {
            channel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //创建一个AIO客户端，并与指定的地址建立连接
        AioClient clientA = new AioClient("127.0.0.1", 8888);
        //向服务端发送数据
        clientA.clientWrite("我是客户端-熊猫一号！");
        //读取服务端返回的数据
        clientA.clientRead();
        //关闭客户端的通道连接
        clientA.clientDown();

        //创建一个AIO客户端，并与指定的地址建立连接
        AioClient clientB = new AioClient("127.0.0.1", 8888);
        //向服务端发送数据
        clientB.clientWrite("我是客户端-熊猫二号！");
        //读取服务端返回的数据
        clientB.clientRead();
        //关闭客户端的通道连接
        clientB.clientDown();
    }
}
