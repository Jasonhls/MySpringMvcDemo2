package com.cn.socketAndNetty3.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @Author: helisen
 * @Date 2021/9/2 13:57
 * @Description:
 */
public class BufferTest {
    @Test
    public void test1() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(6);
        for (int i = 0; i < 6; i++) {
            byteBuffer.put((byte)i);
        }
        byteBuffer.clear();

        while(byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }

        //取数组的第3个和第4个元素，limit指向的是最后一个元素的下一个
        byteBuffer.position(2);
        byteBuffer.limit(4);
    }
}
