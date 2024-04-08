package com.cn.sockeAndNetty4.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @Author: 何立森
 * @Date: 2023/07/28/9:59
 * @Description:
 */
public class Client {
    /**
     * 服务器地址
     */
    public final static String SERVER_ADDRESS = "127.0.0.1";

    /**
     * 服务器监听的端口号
     */
    public static final int PORT = 8888;

    /**
     * 监听SocketChannel通道的选择器
     */
    private Selector selector;

    /**
     * 服务器端的套接字通道，相当于BIO中的ServerSocket
     */
    private SocketChannel socketChannel;

    public Client() {
        initClientSocketChannelAndSelector();
    }

    /**
     * 初始化服务器套接字通道和选择器
     */
    private void initClientSocketChannelAndSelector() {
        try {
            //创建并配置 服务器套接字通道 ServerSocketChannel
            socketChannel = SocketChannel.open(new InetSocketAddress(SERVER_ADDRESS, PORT));
            socketChannel.configureBlocking(false);

            //获取选择器，并注册服务器套接字到ServerSocketChannel
            selector = Selector.open();
            //注册通道：将SocketChannel通道注册给选择器(Selector)
            //关注事件：关注事件时读取事件，服务器端从该通道读取数据
            //关联缓冲区
            socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向服务端发送消息
     * @param message
     */
    public void sendMessageToServer(String message) {
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readMessageFromServer() {
        //阻塞监听，如果有事件触发，返回触发的事件个数
        //被触发的SelectionKey事件都存放到了Set<SelectionKey> selectedKeys集合中
        //下面开始遍历上述selectedKeys集合
        try {
            int select = selector.select();
            System.out.println("select的值为：" + select);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //当前状态说明：
        //如果能执行到该位置，说明selector.select()方法返回值大于0
        //当前有1个或多个事件触发，下面就是处理事件的逻辑

        //处理事件集合
        //获取当前发送的事件的SelectionKey集合，通过SelectionKey可以获取对应的通道
        Set<SelectionKey> keys = selector.selectedKeys();
        //使用迭代器迭代，涉及到删除操作
        Iterator<SelectionKey> keyIterator = keys.iterator();
        while(keyIterator.hasNext()) {
            SelectionKey key = keyIterator.next();
            //根据SelectionKey的事件类型，处理对应通道的业务逻辑
            //客户端写出数据到服务器端，服务器端需要读取数据
            if(key.isReadable()) {
                //获取通道(channel):通过SelectionKey获取
                SocketChannel socketChannel = (SocketChannel)key.channel();
                //获取 缓冲区(Buffer)：获取到通道(Channel)关联的缓冲区(Buffer)
                ByteBuffer byteBuffer = (ByteBuffer)key.attachment();

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
                    System.out.println(String.format(message));
                } catch (IOException e) {
                    //客户端连接端开
                    key.cancel();
                    try {
                        socketChannel.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            //处理完毕后，当前的SelectionKey已经处理完毕
            //从Set集合中移除该SelectionKey
            //防止重复处理
            keyIterator.remove();
        }
    }

    public static void main(String[] args) {

        Client client = new Client();
        //接受服务器端数据的线程
        new Thread(() -> {
            while(true) {
                //不停地从服务器端读取数据
                client.readMessageFromServer();
            }
        }).start();

        //输入消息
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String message = scanner.nextLine();
            //发送消息给服务器端
            client.sendMessageToServer(message);
        }
    }
}
