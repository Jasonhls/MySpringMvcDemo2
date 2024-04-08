package com.cn.sockeAndNetty4.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: 何立森
 * @Date: 2023/07/26/17:47
 * @Description:
 */
public class Server {
    public static final int port = 8888;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public Server() {
        initServerSocketChannelAndSelector();
    }

    private void initServerSocketChannelAndSelector() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
//            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false); //设置成非阻塞的

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void selectorStartSelectOperation() {
        System.out.println("服务端启动监听:");
        while(true) {
            //阻塞监听，如果有事件触发，返回触发的事件个数
            //被触发的SelectionKey事件都存放到了Set<SelectionKey> selectedKeys集合中
            //下面开始遍历上述 selectedKeys集合
            try {
                int select = selector.select();
                System.out.println("select值为：" + select);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //根据SelectionKey的事件类型，处理对应通道的业务逻辑
                //客户端连接服务器，服务器端需要执行accept操作
                if(key.isAcceptable()) {
                    /**
                     * 客户端启动的时候，会连接服务器，然后就会有SelectionKey类型是isAcceptable
                     */

                    //创建通道：为该客户端创建一个对应的SocketChannel通道
                    //非阻塞方法：ServerSocketChannel的accept()是非阻塞的方法
                    SocketChannel socketChannel;
                    try {
                        socketChannel = serverSocketChannel.accept();
                        //如果ServerSocketChannel是非阻塞的，这里的SocketChannel也要设置成非阻塞的
                        //否则会报java.nio.channels.IllegalBlockingModeException异常
                        socketChannel.configureBlocking(false);

                        //注册通道：将SocketChannel通道注册给 选择器(Selector)
                        //关注事件：关注事件时读取事件，服务器端从该通道读取数据
                        //关联缓冲区
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                        System.out.println(String.format("用户 %s 进入聊天室", socketChannel.getRemoteAddress()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                //客户端写出数据到服务器端，服务器端需要读取数据
                if(key.isReadable()) {
                    //获取通道(channel):通过SelectionKey获取
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    //获取 缓冲区(Buffer)：获取到通道(Channel)关联的缓冲区(Buffer)
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();

                    String remoteAddress;
                    String message;
                    try {
                        //读取客户端传输的数据
                        int readCount = socketChannel.read(byteBuffer);
                        byte[] messageBytes = new byte[readCount];
                        byteBuffer.flip();
                        byteBuffer.get(messageBytes);
                        //处理读取的消息
                        message = new String(messageBytes);
                        byteBuffer.flip();
                        remoteAddress = socketChannel.getRemoteAddress().toString();
                        System.out.println(String.format("%s : %s", remoteAddress, message));
                    } catch (IOException e) {
                        //如果此处出现异常，说明该客户端离线了，服务器提示，取消选择器上的注册信息，关闭通道
                        try {
                            /**
                             * 注意客户端服务关闭的时候，SelectionKey类型还是isReadable()，但是不能读取到数据，会报错，因此会走到这里
                             */
                            System.out.println(String.format("%s 用户离线！", socketChannel.getRemoteAddress()));
                            key.cancel();
                            socketChannel.close();
                            //继续下一次循环
                            continue;
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    //向其他客户端转发消息，发送消息的客户端自己就不用再发送该消息了
                    //遍历所有注册到选择器Selector的SocketChannel
                    Set<SelectionKey> selectionKeys = selector.keys();
                    for (SelectionKey selectionKey : selectionKeys) {
                        //获取客户端对应的套接字通道
                        /**
                         * 这里不能强转成SocketChannel，因为这里可能是ServerSocketChannel，
                         * 也可能是自己这个SocketChannel，也可能是其余的SocketChannel
                         */
                        SelectableChannel channel = selectionKey.channel();
                        //将自己排除在外（因为不需要将消息再转发给发送者，而是转发给其他客户端），注意这里是地址对比，就是这两个类不能是同一个地址的类
                        //这个类的类型必须是SocketChannel，排除之前注册的ServerSocketChannel干扰
                        if(channel != socketChannel && channel instanceof SocketChannel) {
                            //将通道转换为SocketChannel，之后将字符串发送到客户端
                            SocketChannel clientSocketChannel = (SocketChannel) channel;
                            //写出字符串到其他客户端
                            try {
                                /**
                                 * 客户端之间的消息相互发送，其实是通过服务器端转发的，服务器端收到任何一个客户端的消息，都会转发给其余客户端，其余客户端就会收到这条消息
                                 */
                                clientSocketChannel.write(ByteBuffer.wrap((remoteAddress + " : " + message).getBytes()));
                            } catch (IOException e) {
                                //如果此处出现异常，说明该客户端离线了，服务器提示，取消选择器上的注册信息，关闭通道
                                try {
                                    System.out.println(String.format("%s 用户离线！", clientSocketChannel.getRemoteAddress()));
                                    selectionKey.cancel();
                                    clientSocketChannel.close();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    }
                }
                //处理完毕后，当前的SelectionKey已经处理完毕
                //从Set集合中移除该SelectionKey
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.selectorStartSelectOperation();
    }
}
