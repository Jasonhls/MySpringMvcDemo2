package com.cn.socketAndNetty3.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Author: helisen
 * @Date 2021/9/2 9:37
 * @Description:
 */
public class NioClient {

    private static ByteBuffer buffer1 = ByteBuffer.allocate(1024);

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8888));
            if(socketChannel.finishConnect()) {
                int i = 0;
                while(true) {
                    TimeUnit.SECONDS.sleep(50);

                    i = writeRequestToServer(buffer, socketChannel, i);

                    TimeUnit.SECONDS.sleep(2);

                    //处理来自服务端的响应
                    handleResponseFromServer(socketChannel);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 发送请求到服务端
     * @param buffer
     * @param socketChannel
     * @param i
     * @return
     * @throws IOException
     */
    private static int writeRequestToServer(ByteBuffer buffer, SocketChannel socketChannel, int i) throws IOException {
        buffer.clear();//打印顺序：mark position limit capacity //刚开始是-1 0 1024 1024，经过一次循环变成了-1 31 31 1024 //执行后变成：-1 0 1024 1024
        //发送请求到服务端
        String info = "I'm" + (i++) + "-th information from client";
        //把数据放入buffer中
        buffer.put(info.getBytes(StandardCharsets.UTF_8));   // 执行后变成：-1 31 1024 1024
        buffer.flip();   //执行后变成：-1 0 31 1024
        while(buffer.hasRemaining()) {
            StringBuilder builder = new StringBuilder();
            builder.append(buffer.mark()).append("_").append(buffer.position()).append("_").append(buffer.limit()).append("_").append(buffer.capacity());
            System.out.println(builder.toString());
            //将buffer中的数据写入通道中
            socketChannel.write(buffer); //执行后变成：-1 31 31 1024
        }
        return i;
    }

    /**
     * 对服务端进行响应
     * @param socketChannel
     * @throws IOException
     */
    private static void handleResponseFromServer(SocketChannel socketChannel) throws IOException {
        socketChannel.read(buffer1);
        buffer1.flip();
        System.out.print("客户端打印服务端响应的数据：");
        while(buffer1.hasRemaining()) {
            System.out.print((char) buffer1.get());
        }
        System.out.println("---------end--------");
        buffer1.clear();
    }
}
