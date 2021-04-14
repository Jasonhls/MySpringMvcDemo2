package com.cn.socketAndNetty2.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 10:17
 **/
public class NettyByteBuf01 {
    public static void main(String[] args) {
        //先创建一个 ByteBuf
        //说明：
        //1.创建对象，该对象包含一个数组arr，是一个byte[10]
        //2.在netty的buffer中不需要使用flip进行反转，而NIO中的ByteBuffer需要，因为netty的ByteBuf底层
        //维护了 readerIndex（下一个读取的位置） 和 writerIndex（下一个写入的位置）
        //3.通过readerIndex 和 writerIndex 和 capacity 将 buffer分成了三个区
        // 0 --- readerIndex，已经读取的区域
        // readerIndex --- writerIndex，可读取的区域
        // writerIndex --- capacity，可写的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            //writeByte会使writerIndex发生变化，每写一次，就加一。
            buffer.writeByte(i);
        }

        System.out.println("capacity=" + buffer.capacity());//10
        //输出
        for (int i = 0; i < buffer.capacity(); i++) {
//            System.out.println(buffer.getByte(i)); //getByte不会时readerIndex发生改变
            //readBytes会使readerIndex发生变化，每读一次，就加一。
            System.out.println(buffer.readBytes(i));
        }
    }
}
