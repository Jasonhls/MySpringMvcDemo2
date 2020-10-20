package com.cn.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @description: 可以将多个Channel注册到同一个Selector对象上，实现一个线程同时监控多个Channel的请求状态，但有一个缺陷，所有的
 * 读写以及新连接请求的处理都在同一个线程中处理，无法充分利用多CPU的优势，同时读/写操作也会阻塞对新连接请求的处理，其中一种优化策略是
 * 可以使用Reactor模式
 * @author: helisen
 * @create: 2020-10-19 09:42
 **/
public class NioServer implements Runnable{

    public static void main(String[] args) {
        new Thread(new NioServer(8379)).start();
    }


    private Selector selector;
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public NioServer(int port) {
        try {
            /**
             * 1.获取服务器套字节通道
             */
            ServerSocketChannel ssc = ServerSocketChannel.open();
            /**
             * 2.切换成非阻塞模式
             */
            ssc.configureBlocking(false);
            /**
             * 3.绑定端口
             */
            ssc.bind(new InetSocketAddress(port));
            /**
             * 4.获取选择器
             */
            selector = Selector.open();
            /**
             * 5.将channel注册到selector中
             * 通常我们都是先注册一个OP_ACCEPT事件，然后在OP_ACCEPT到来时，再将这个channel的OP_READ注册到Selector中
             */
            ssc.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            /**
             * 6.轮询获取选择器上已经准备就绪的事件，
             * 事件包括客户端连接，客户端发送数据到来，以及客户端断开连接等等
             *
             * int select = selector.select();
             * selector.select()方法返回的数值代表就绪Channel的数量，如果没有Channel就绪，就一直阻塞。
             * int select = selector.select(1000)
             * selector.select(timeout)方法，最长阻塞timeout毫秒，超时后返回0，表示没有通道(Channel)就绪。
             */
            while(selector.select() >0) {
                /**
                 * 7.获取当前选择器中所有注册的选择链
                 */
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                //遍历key
                while(keyIterator.hasNext()) {
                    /**
                     * 8.获取准备就绪的事件
                     */
                    SelectionKey key = keyIterator.next();

                    if(key.isValid()) {                    //判断key是否有效
                        /**
                         * 9.判断具体是什么事件准备就绪
                         */
                        if(key.isAcceptable()) {      //请求连接事件，客户端连接，会执行这里面的方法
                            System.out.println("key is acceptable");
                            accept(key);                   //处理新客户的连接
                        }
                        if(key.isReadable()) {        //有数据到来
                            System.out.println("key is readable");
                            read(key);
                        }
                    }
                    /**
                     * 当获取一个SelectionKey，就要将它删除，表示我们已经对这个IO时间做了处理了
                     */
                    keyIterator.remove();
                    System.out.println("-------------这次事件处理完了------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理客户端连接
     * 服务器为每个客户端生成一个Channel
     * Channel与客户端对接
     * Channel绑定到Selector上
     * @param key
     */
    private void accept(SelectionKey key) {
        try {
            /**
             * 当OP_ACCEPT事件到来时，就可以从ServerSocketChannel中获取一个SocketChannel，代表客户端的连接
             * 注意：当OP_ACCEPT事件中，从key.channel()返回Channel是ServerSocketChannel，
             * 而在OP_WRITE和OP_READ中，从key.channel()返回的是SocketChannel。
             */
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();//这里的ServerSocketChannel对象与NioServer构造函数中的ServerSocketChannel是同一个对象
            /**
             * 10.若接受就绪，获取客户端连接
             */
            SocketChannel sc = ssc.accept();
            /**
             * 11.切换到非阻塞模式
             */
            sc.configureBlocking(false);
            /**
             * 12.将该通道注册到选择器上
             * 在OP_ACCEPT到来时，再将这个Channel的OP_READ注册到Selector中，
             * 注意：如果我们这里没有设置OP_READ的话，即interest set仍然是OP_CONNECT的话，那么select方法会一直直接返回
             */
            sc.register(selector, SelectionKey.OP_READ);
            System.out.println("成功连接客户端");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理数据
     * @param key
     */
    private void read(SelectionKey key) {
        try {
            /**
             * 13.获取当前选择器上"读就绪"状态的通道
             */
            SocketChannel sc = (SocketChannel) key.channel();
            /**
             * 14.读取数据
             */
            int len;
            while((len = sc.read(buffer)) > 0) {
                buffer.flip();//将缓存的写模式切换到读模式
                System.out.println(new String(buffer.array(), 0, len));
                buffer.clear();//清空缓存

                /**
                 * 立马给客户端作出响应
                 */
                writeBuffer.put("已经完成数据的读取".getBytes());
                //上面向缓存中写入数据，position位置变化了，需要回到初始位置，使用flip方法
                writeBuffer.flip();
                sc.write(writeBuffer);
                writeBuffer.clear();
            }
            System.out.println("read over");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
