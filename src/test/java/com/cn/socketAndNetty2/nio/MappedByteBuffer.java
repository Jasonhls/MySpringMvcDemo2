package com.cn.socketAndNetty2.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class MappedByteBuffer {
    /**
     *直接在内存中修改文件内容
     */
    public static void main(String[] args) throws Exception{
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\file01.txt", "rw");
        //获取对应的通道
        FileChannel channel = randomAccessFile.getChannel();
        java.nio.MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte)'H');
        mappedByteBuffer.put(0, (byte)'9');

        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
