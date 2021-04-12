package com.cn.socketAndNetty2.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello,硅尚古";
        //创建一个输出流 -> channel
        FileOutputStream fos = new FileOutputStream("d:\\file01.txt");
        //通过FileOutputStream获取对应的FilChannel(真实类型为FileChannelImpl)
        FileChannel fileChannel = fos.getChannel();
        //创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //将str放入byteBuffer中
        byteBuffer.put(str.getBytes());
        //对byteBuffer进行flip
        byteBuffer.flip();

        //将byteBuffer数据写入到fileChannel
        fileChannel.write(byteBuffer);
        fos.close();

    }
}
