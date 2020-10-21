package com.cn.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledHeapByteBuf;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * @description: ByteBuf的学习使用
 * Java NIO提供了ByteBuffer作为它的字节容器，但是这个类使用起来过于复杂，而且也有些繁琐。
 * Netty中使用ByteBuf替代ByteBuffer
 * ByteBuf的优点：
 * 它可以被用户自定义的缓冲区类型扩展
 * 通过内置的复合缓冲区类型实现了透明的零拷贝
 * 容量可以按需增长（类似于JDK的StringBuilder）
 * 在读和写这两种模式之间切换不需要调用ByteBuffer的flip()方法
 * 读和写使用了不同的索引
 * 支持方法的链式调用
 * 支持引用计数
 * 支持池化
 *
 * ByteBuf和JDK中的ByteBuffer的区别：
 * （1）netty的ByteBuf采用读/写索引分离，一个初始化的ByteBuf的readerIndex和writerIndex都处于0位置
 * （2）当读索引和写索引处于同一位置时，如果我们继续读取，就会抛出异常IndexOutOfBoundsException
 * （3）对于ByteBuf的任何读写操作都会分别单独的维护读索引和写索引。maxCapacity最大容量默认的限制就是Integer.MAX_VALUE。
 *
 * ByteBuf的使用模式：
 * JDK中的Buffer类型有heapBuffer和directBuffer两种类型，但是在netty中除了heap和direct类型，还有composite Buffer（复合缓冲类型）
 *
 * @author: helisen
 * @create: 2020-10-21 17:10
 **/
public class ByteBufTest {

    /**
     * Heap Buffer 堆缓冲区 使用
     * 优点：由于数据存储在JVM的堆中可以快速创建和快速释放，并且提供了数组的直接快速访问的方法
     * 缺点：每次读写都要先将数据拷贝到直接缓冲区再进行传递
     */
    @Test
    public void test1() {
        ByteBuf heapBuf = Unpooled.buffer();
        heapBuf.writeBytes("大家好吗".getBytes());
        if(heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            System.out.println(new String(array, offset, length));
        }
    }

    /**
     * Direct Buffer 直接缓冲区 使用
     * Direct Buffer在堆之外直接分配内存，直接缓冲区不会占用堆的容量。事实上，在通过套字节发送它之前，
     * JVM将会在内部把你的缓冲区复制到直接缓冲区。所以如果使用直接缓冲区，可以节约一次拷贝。
     * 优点：在使用Socket传递数据时性能很好，由于数据直接在内存中，不存在从JVM拷贝数据到直接缓冲区的过程，性能好
     * 缺点：相对于基于堆的缓冲区，它们的分配和释放较为昂贵。如果你正在处理遗留代码，
     * 你也可能会遇到另外一个缺点：因为数据不在堆上，所以不得不进行一次复制。
     * netty通过内存池来解决这个问题。直接缓冲池不支持数组访问数据，但可以通过间接的方式访问数据数组。
     */
    @Test
    public void test2() {
        ByteBuf directBuf = Unpooled.directBuffer();
        directBuf.writeBytes("我是DirectBuf".getBytes());
        if(!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            System.out.println(new String(array, 0, length));
        }
    }

    /**
     * Composite Buffer 复合缓冲区
     */
    @Test
    public void test3() {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();
        ByteBuf heapBuf = Unpooled.buffer(8);
        heapBuf.writeBytes("我是HeapBuffer".getBytes());
        ByteBuf directBuf = Unpooled.directBuffer(16);
        directBuf.writeBytes("我是DirectBuffer".getBytes());
        compBuf.addComponents(heapBuf, directBuf);
        //删除第一个ByteBuf
        compBuf.removeComponent(0);
        Iterator<ByteBuf> iterator = compBuf.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }

        //使用数组访问数据
        if(!compBuf.hasArray()) {
            int length = compBuf.readableBytes();
            byte[] array = new byte[length];
            compBuf.getBytes(compBuf.readerIndex(), array);
            System.out.println(new String(array, 0, length));
        }
    }
}
