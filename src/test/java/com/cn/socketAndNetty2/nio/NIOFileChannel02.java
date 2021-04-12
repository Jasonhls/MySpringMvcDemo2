package com.cn.socketAndNetty2.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        //创建文件的输入流
        File file = new File("d:\\file01.txt");
        FileInputStream fis = new FileInputStream(file);

        //通过FileInputStream获取对应的fileChannel
        FileChannel fileChannel1 = fis.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        //将通道的数据写入到Buffer
        fileChannel1.read(byteBuffer);

        //将byteBuffer的字节数据转成String
        System.out.println(new String(byteBuffer.array()));
        fis.close();
    }
}
