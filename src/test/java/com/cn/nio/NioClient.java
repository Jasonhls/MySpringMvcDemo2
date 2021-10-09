package com.cn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/10/8 18:15
 * @Description:
 */
public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 9999));

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //请求数据：数据 ---写入---> buffer ---读出数据，并写入---> SocketChannel  注意：buffer从写模式切换到读模式，需要调用flip方法
        String newData = "New String to write to file...." + System.currentTimeMillis();
        buffer.put(newData.getBytes(StandardCharsets.UTF_8)); //往Buffer中写入数据

        buffer.flip();// make buffer ready for read

        while(buffer.hasRemaining()) {
            socketChannel.write(buffer); //从Buffer中读数据出来，并写到SocketChannel中
        }
        buffer.compact(); //清除buffer中读过的数据，并且make buffer ready for write


        //处理服务端响应回来的数据：SocketChannel ---读出数据，并写入---> buffer ---读出数据---> 打印数据  注意：buffer从写模式切换到读模式，需要调用flip方法
        socketChannel.read(buffer); //从SocketChannel中读数据出来，并写到Buffer中
        buffer.flip();// make buffer ready for read
        while(buffer.hasRemaining()) {
            System.out.print((char) buffer.get()); //从Buffer中读数据出来
        }
        System.out.println();
        buffer.compact(); //清除读过的数据

        socketChannel.close();
    }
}
