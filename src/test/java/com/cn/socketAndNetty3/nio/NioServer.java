package com.cn.socketAndNetty3.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Random;

/**
 * @Author: helisen
 * @Date 2021/9/1 17:20
 * @Description:
 */
public class NioServer {

    private static int TIMEOUT = 1000;
    private static int BUF_SIZE = 1024;
    private static ByteBuffer buffer1 = ByteBuffer.allocate(1024);

    /**
     * 1.打开ServerSocketChannel，监听客户端连接
     * 2.绑定监听端口，设置连接为非阻塞模式
     * 3.创建Reactor线程，创建多路复用器并启动线程
     * 4.将ServerSocketChannel注册到Reactor线程中的Selector上，监听ACCEPT事件
     * 5.Selector轮询准备就绪的key
     * 6.Selector监听到新的客户端接入，处理新的接入请求，完成TCP三次握手
     * 7.设置客户端链路为非阻塞模式
     * 8.将新接入的客户端连接注册到Reactor线程的Selector上，监听读操作，读取客户端发送的网络消息
     * 9.异步读取客户端消息到缓冲区
     * 10.对Buffer编解码，处理半包消息，将解码成功的消息封装成Task
     * 11.将应答消息编码为Buffer，调用SocketChannel的write将消息异步发送给客户端
     */
    public static void main(String[] args) {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;
        try {
            int port = 8888;
            //创建选择器（返回的对象为WindowsSelectorImpl对象）
            selector = Selector.open();
            //打开监听通道（返回的对象为ServerSocketChannelImpl对象）
            serverSocketChannel = ServerSocketChannel.open();
            //开启非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定端口，backlog大小设为1024，socket()方法返回的为ServerSocketAdaptor对象
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //监听客户端连接请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //循环遍历selector，处理来自客户端的请求
            while(true) {
                if(selector.select(TIMEOUT) == 0) {
//                    System.out.println("超时");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //处理连接请求
                    if(key.isAcceptable()) {
                        handleAccept(key);
                    }
                    //处理读请求
                    if(key.isReadable()) {
                        handleRead(key);
                    }
                    //处理写请求
                    if(key.isWritable() && key.isValid()) {
                        handleWrite(key);
                    }
                    //处理判断是否连接的请求
                    if(key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(selector != null) {
                    selector.close();
                }
                if(serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理写请求
     * @param key
     * @throws IOException
     */
    private static void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        while(buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        //清除buffer中已经读过的数据
        buffer.compact();
    }

    /**
     * 处理读请求
     * @param key
     * @throws IOException
     */
    private static void handleRead(SelectionKey key) throws IOException{
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();//刚开始值为：-1 0 1024 1024
        int bytesRead;
        System.out.print("客户端开始读取来自服务端的请求数据：");
        while((bytesRead = socketChannel.read(buffer)) > 0) {
            //切换读模式为写模式
            buffer.flip();
            //将buffer中的元素全部读完，读完才会跳出循环
            while(buffer.hasRemaining()) {
                System.out.print((char)buffer.get());
            }
            System.out.println();
            buffer.clear();//执行后变成：-1 0 1024 1024
        }

        //做出响应
        doResponseToClient(socketChannel);

        if(bytesRead == -1) {
            socketChannel.close();
        }

    }

    /**
     * 处理新的连接
     * @param key
     */
    private static void handleAccept(SelectionKey key) throws IOException{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        //通过ServerSocketChannel的accept创建SocketChannel实例
        //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
        SocketChannel socketChannel = serverSocketChannel.accept();
        //设置为非阻塞
        socketChannel.configureBlocking(false);
        //这里新创建了一个ByteBuffer与该SelectionKey绑定，后面可以直接根据SelectionKey获取
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(BUF_SIZE));
    }

    /**
     * 对客户端进行响应
     * @param socketChannel
     * @throws IOException
     */
    private static void doResponseToClient(SocketChannel socketChannel) throws IOException {
        buffer1.put(("123456789_" + new Random().nextInt(10)).getBytes(StandardCharsets.UTF_8));
        buffer1.flip();
        socketChannel.write(buffer1);
        buffer1.clear();
    }
}
