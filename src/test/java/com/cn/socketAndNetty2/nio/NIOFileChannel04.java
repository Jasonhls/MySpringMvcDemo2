package com.cn.socketAndNetty2.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream fis = new FileInputStream("d:\\1.jpg");
        FileOutputStream fos = new FileOutputStream("d:\\2.jpg");

        FileChannel sourceChannel = fis.getChannel();
        FileChannel destChannel = fos.getChannel();

        //使用transferForm完成拷贝
        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        //关闭相关的通道和流
        sourceChannel.close();
        destChannel.close();
        fis.close();
        fos.close();
    }
}
