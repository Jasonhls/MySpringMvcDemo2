package com.cn.socket.nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @description:
 * 一、缓冲区（buffer）：在Java NIO中负责数据的存取，缓冲区就是数组，用于存储不同数据类型的数据
 * <p>
 * 根据数据类型的不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * IntBuffer
 * LongBuffer
 * ShortBuffer
 * FloatBuffer
 * DoubleBuffer
 * CharBuffer
 * <p>
 * 上述缓冲区的管理方式几乎一致，通过allocate*()获取缓冲区
 * <p>
 * 二、缓冲区存取数据的两个核心方法
 * put()：存入数据到缓冲区
 * get()：获取缓冲区数据。
 * <p>
 * 三、缓冲区中的四个核心属性、
 * capacity：容量，
 * limit:界限，表示缓冲区中可以操作数据的大小，（limit后的数据不能进行读写）
 * position：位置，表示缓冲区中正在操作数据的位置
 * mark：标记，表示记录当前position的位置，可以通过reset()使position恢复到mark位置。
 * 0 <= mark <= position <= limit <=capacity
 *
 * @author: helisen
 * @create: 2020-10-19 14:15
 **/
public class TestBuffer {
    @Test
    public void test2() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);// mark -1 position 0 limit 1024 capacity 1024
        buffer.put("abcde".getBytes());//mark -1 position 5 limit 1024 capacity 1024
        System.out.println(buffer.position());

        /**
         * mark还是-1，position变成0，limit变成5，capacity还是1024
         */
        buffer.flip();
        //buffer.limit()为5，初始化字节数组，五个元素默认值都为0
        byte[] dts = new byte[buffer.limit()];
        //从buffer拷贝length个元素到字节数组dts中，然后从数组索引为offset的位置开始存储拷贝过来的元素
        //操作了三个元素，position为3，把这三个元素放入dts数组中，从索引位置为2开始存放
        buffer.get(dts, 2, 3);
        //打印数组元素，从索引位置为offset开始，打印length个元素。
        System.out.println(new String(dts, 0, 5));
        //由于前面操作了三个元素，因此buffer.position为3
        System.out.println(buffer.position());
        //mark标记一下，记录当前position的位置，即mark由初始-1变成了3
        buffer.mark();

        //又拷贝两个元素，从buffer的position后面第一个元素开始，然后拷贝放入dts数组中，从数组的索引为offset位置开始存放
        buffer.get(dts, 1, 2);
        System.out.println(new String(dts, 0, 5));
        //又操作了两个元素之后，position变成了5
        System.out.println(buffer.position());

        /**
         * 恢复position到mark标记处，
         * 因为上面的原因，mark为3，reset使position的值又变成了3
         */
        buffer.reset();
        //又拷贝一个数据，position变成了4，放入数组索引为4的位置
        buffer.get(dts, 4, 1);
        System.out.println(new String(dts, 0, 5));
        System.out.println(buffer.position());
        System.out.println(new String(dts));

        //判断缓冲区是否还有剩余元素
        if(buffer.hasRemaining()) {
            //获取缓冲区中剩余数据的数量
            System.out.println(buffer.remaining());
        }
    }

    @Test
    public void test() {
        //1.分配一个指定大小的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println("---------allocate()---------");
        System.out.println(buffer.position()); //0
        System.out.println(buffer.limit());       //1024
        System.out.println(buffer.capacity()); //1024

        //2.使用put()存入数据到缓冲区
        String string = "abcde";
        buffer.put(string.getBytes());
        System.out.println("----------put()-------------");
        System.out.println(buffer.position()); //5
        System.out.println(buffer.limit());       //1024
        System.out.println(buffer.capacity()); //1024

        //3.切花读取数据模式
        /**
         * position恢复到初始值0，limit设置为缓存中元素实际个数
         */
        buffer.flip();
        System.out.println("----------flip()-------------");
        System.out.println(buffer.position()); //0
        System.out.println(buffer.limit());       //5
        System.out.println(buffer.capacity()); //1024

        //4.读取数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println("--------打印字节数组----------");
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("----------flip()-------------");
        System.out.println(buffer.position()); //5
        System.out.println(buffer.limit());       //5
        System.out.println(buffer.capacity()); //1024

        //5.rewind()：可重复读
        /**
         * 将position变成了0，可以重新第一个元素开始读
         */
        buffer.rewind();
        System.out.println("----------rewind()---------------");
        System.out.println(buffer.position()); //0
        System.out.println(buffer.limit());       //5
        System.out.println(buffer.capacity()); //1024

        //6.clear()，清空缓冲区，缓冲区中的数据还存在，但是出于"被遗忘"状态。
        buffer.clear();//limit变成了1024
        buffer.rewind();//将position变成初始位置，即0
        System.out.println("----------clear()---------------");
        System.out.println(buffer.position()); //0
        System.out.println(buffer.limit());       //5
        System.out.println(buffer.capacity()); //1024
        System.out.println((char) buffer.get());
    }
}
