package com.cn.socketAndNetty2.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception{
        //1.得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //2.设置非阻塞
        socketChannel.configureBlocking(false);
        //3.提供服务端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //4.连接服务器端
        if(!socketChannel.connect(inetSocketAddress)) {
            while(!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他操作");
            }
        }

        //如果连接成功，就发送数据
        String str = "hello,硅尚古";
        //把一个字节数组放到一个buffer中
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将buffer 数据写入 channel
        socketChannel.write(buffer);

        //暂停
        System.in.read();
    }
}
