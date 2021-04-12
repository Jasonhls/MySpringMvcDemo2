package com.cn.socketAndNetty2.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel --> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selector对象
        Selector selector = Selector.open();
        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        //设置ServerSocketChannel为非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel注册到 selector 关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while(true) {
            //这里我们等待1秒，如果没有事件发生，返回
            if(selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }

            //如果返回大于0，就获取相关的selectionKey集合
            //1.如果返回的>0，表示已经获取到关注的事件
            //2.selector.selectedKeys()返回关注事件的集合
            //通过selectionKeys反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            //遍历Set<SelectionKey>，使用迭代器
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = iterator.next();
                //根据key对应的通道发生的事件做相应的处理
                if(key.isAcceptable()) {//如果是OP_ACCEPT，有新的客户端连接
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个socketChannel " + socketChannel.hashCode());
                    //将socketChannel设置为非阻塞的
                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector，关注事件为OP_READ，
                    //同时给socketChannel关联一个Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后，注册的selectionKey数量=" + selector.keys().size());
                }

                if(key.isReadable()) {//发生OP_READ
                    //通过key 反向获取对应channel
                    SocketChannel channel = (SocketChannel)key.channel();
                    //获取该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    //把当前的数据读到buffer中
                    channel.read(buffer);
                    System.out.println("form 客户端 " + new String(buffer.array()));
                }

                //手动从集合中移动当前的selectionKey，防止重复操作
                iterator.remove();
            }
        }
    }
}
