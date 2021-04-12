package com.cn.socketAndNetty2.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream fis = new FileInputStream("d:\\file01.txt");
        FileChannel fileChannel01 = fis.getChannel();

        FileOutputStream fos = new FileOutputStream("d:\\file02.txt");
        FileChannel fileChannel02 = fos.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true) {
            byteBuffer.clear();
            //从buffer中读取数据
            int read = fileChannel01.read(byteBuffer);
            System.out.println("read=" + read);
            if(read == -1) {
                break;
            }
            //将buffer中的数据吸入到fileChannel02 --> 2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }

        //关闭相关的流
        fis.close();
        fos.close();
    }
}
