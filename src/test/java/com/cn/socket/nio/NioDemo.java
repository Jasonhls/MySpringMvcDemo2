package com.cn.socket.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 16:46
 **/
public class NioDemo {
    public static void main(String[] args) throws Exception{
        File file = new File("E:\\uploadTest\\abc.txt");
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = channel.read(buf);
        while(bytesRead != -1) {
            System.out.println("Read：" + bytesRead);
            buf.flip();
            while(buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = channel.read(buf);
        }
        channel.close();
        fis.close();
    }
}
