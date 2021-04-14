package com.cn.socketAndNetty2.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 10:29
 **/
public class NettyByteBuf02 {
    public static void main(String[] args) {
        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);
        //使用相关的方法
        if(byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            //将content转成字符串
            System.out.println(new String(content, CharsetUtil.UTF_8));

            System.out.println("byteBuf=" + byteBuf);
            System.out.println(byteBuf.arrayOffset());//0
            System.out.println(byteBuf.readerIndex());//0
            System.out.println(byteBuf.writerIndex());//12
            System.out.println(byteBuf.capacity());//64

//            System.out.println(byteBuf.getByte(0));//这种不会影响readerIndex
            System.out.println(byteBuf.readByte());//读出一个，H对应ASCII码为104

            int len = byteBuf.readableBytes();//可读取的字节数  11
            System.out.println(len);

            //使用for循环取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            //读取部分字符，从索引为4开始读取，6表示往后读6个字符
            System.out.println(byteBuf.getCharSequence(4, 6, CharsetUtil.UTF_8));


        }
    }
}
