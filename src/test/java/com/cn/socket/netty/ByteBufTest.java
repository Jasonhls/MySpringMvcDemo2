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
     * Netty使用了CompositeByteBuf来优化套接字的I/O操作，尽可能消除了有JDK的缓冲区实现所导致的性能以及内存使用率的惩罚。
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
            /**
             * 由于上面执行过writeBytes，因此writableBytes()返回的写索引是directBuf中内容的长度，
             * 而readableBytes()返回的读索引值仍然是0
             */
            int length = compBuf.writableBytes();
            byte[] array = new byte[length];
            compBuf.getBytes(0, array);
            System.out.println(new String(array, 0, length));
        }
    }

    /**
     * ByteBuf提供读/写索引，从0开始的索引，第一个字节索引是0，最后一个字节的索引是capacity-1
     * heapBuf.capacity()方法返回是heapBuf中数组array的长度
     *
     * 对于已经读过的字节，我们需要回收，通过调用ByteBuf.discardReadBytes()来回收已经读取过的字节，
     * discardReadBytes()将回收从索引0到readerIndex之间的字节。这个方法可以确保可写分段的最大化，它会导致内存的复制，
     * 需要移动ByteBuf中可读字节到开始位置，所以该操作会导致时间开销。
     */
    @Test
    public void test4() {
        //创建一个16字节的buffer，这里默认是创建heap buffer
        ByteBuf heapBuf = Unpooled.buffer(16);
        //写数据到buffer
        for (int i = 0;  i < 16; i++) {
            heapBuf.writeByte(i + 1);

        }
        //读数据
        for (int i = 0; i < heapBuf.capacity(); i++){
            /**
             * heapBuf.getByte(i)不会改变真实的读索引和写索引
             * 可以通过ByteBuf的readerIndex()和writerIndex()方法来分别推进读索引和写索引
             * heapBuf.readByte();  这个方法可以推进读索引readerIndex
             *
             */
            System.out.println(heapBuf.getByte(i) + ", ");
        }
    }

    /**
     * 可读字节方法源码：
     * @Override
     * public boolean isReadable() {
     *     return writerIndex > readerIndex;
     * }
     * 可写字节方法源码：
     * @Override
     * public boolean isWritable() {
     *     return capacity() > writerIndex;
     * }
     * 清除缓冲区
     *@Override
     * public ByteBuf clear() {
     *     readerIndex = writerIndex = 0;
     *     return this;
     * }
     * 标记Mark和重置reset
     * 每个ByteBuf有两个标记索引
     * private int markedReaderIndex;   //标记读索引
     * private int markedWriterIndex;   //标记写索引
     *
     * 衍生的缓冲区
     * 调用duplicate()、slice()、slice(int index, int length)等方法可以创建一个现有缓冲区的视图(现有缓冲区与原有缓冲区是指向相同内存)，
     * 衍生的缓冲区有独立的readerIndex和writerIndex和标记索引，如果需要现有的换缓冲区的全部副本，可以使用copy()获得。
     *
     * ByteBuffer的缺点：
     * ByteBuffer的字节数组是final，也就是长度固定，一旦分配完成就不能扩容和收缩，灵活性低，而且当待存储对象字节很大可能出现数组越界，
     * 用户使用起来稍不小心会出现异常。如果要避免越界，在存储之前就要求字节大小，如果buffer的空间不够就创建一个更大的新的ByteBuffer，
     * 再将之前的ByteBuffer复制过去，效率很低。
     */
}
