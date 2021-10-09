package com.cn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: helisen
 * @Date 2021/10/8 18:14
 * @Description:
 *  Java NIO: Channels read data into Buffers, and Buffers write data into Channels
 *
 */
public class NioServer {
    /**
     * Writing Data to a Buffer:
     * 1.Write data from a Channel into a Buffer
     *   int bytesRead = inChannel.read(buf);
     * 2.Write data into the Buffer yourself, via the buffer's put() methods
     *  buf.put("哈哈");
     *
     *
     * Reading Data from a Buffer:
     * 1. Read data from the buffer into a channel
     *  int bytesWritten = inChannel.write(buf);
     * 2. Read data from the buffer yourself, using one of the get() methods
     *  byte aByte = buf.get();
     *
     */
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        //把serverSocketChannel注册到 selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            int readyChannels = selector.select(1000);
            if(readyChannels == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if(key.isAcceptable()) {
                    handleAccept(key);
                }else if(key.isConnectable()) {
                    handleConnection(key);
                }else if(key.isReadable()) {
                    handleReadable(key);
                }else if(key.isWritable()) {
                    handleWritable(key);
                }
                iterator.remove();
            }
        }
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        //注册读事件
        //register方法最后一个参数，创建一个ByteBuffer对象并赋值给attachment
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    }

    private static void handleConnection(SelectionKey key) {
        System.out.print("connection---------");
        System.out.print(key.channel());
        System.out.print("     ");
        System.out.println(key.selector());
    }

    private static void handleReadable(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();

        //处理读事件：SocketChannel ---读出数据并写入---> buffer ---读出---> 打印数据，重复上面的步骤 注意：buffer从写模式切换到读模式，需要调用flip方法

        //从SocketChannel里面读数据出来，写到buffer中
        while(socketChannel.read(buffer) > 0) {

            buffer.flip();//make buffer ready for read

            while(buffer.hasRemaining()) {
                System.out.print((char) buffer.get()); //read 1 byte at a time
            }
            System.out.println();
            //clear与compact方法的区别是：clear是清除Buffer中所有的数据，compact是清除Buffer中已读的数据，未读的数据会移动到Buffer的最前面
            buffer.clear(); //make buffer ready for writing
        }

        //响应给客户端：数据 ---写入---> buffer ---读出数据并写入---> SocketChannel 注意：buffer从写模式切换到读模式，需要调用flip方法
        buffer.put(("hello, i am the server" + System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8)); //往buffer里面写数据
        buffer.flip();//make buffer ready for read
        socketChannel.write(buffer);// 从Buffer里面读数据出来，写到SocketChannel中

        buffer.compact();//清除buffer中读过的数据

        socketChannel.close();
    }

    private static void handleWritable(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip(); //make buffer ready for read
        while(buffer.hasRemaining()) {
            socketChannel.write(buffer); //将Buffer里面的数据读出来，并写到SocketChannel通道中
        }
        //会把没读的数据拷贝到buffer的最前面，然后写数据的时候，从没读的数据后面开始写
        buffer.compact();
    }
}
