package com.cn.socketAndNetty3.nio.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: helisen
 * @Date 2021/9/2 15:07
 * @Description:
 */
public class FileNIOTest {
    public static void main(String[] args) {
        RandomAccessFile accessFile = null;
        FileChannel fileChannel = null;
        try {
            accessFile = new RandomAccessFile("C:\\Users\\EDZ\\Desktop\\io_test\\io_test1.txt", "rw");
            fileChannel = accessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int bytesRead;
            while((bytesRead = fileChannel.read(byteBuffer)) != -1) {
                System.out.println("bytesRead：" + bytesRead);
                byteBuffer.flip();//从读模式切换到写模式
                while(byteBuffer.hasRemaining()) {
                    System.out.println((char)byteBuffer.get());
                }
                //清除已经读过的数据
                byteBuffer.compact();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fileChannel != null) {
                try {
                    fileChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(accessFile != null) {
                try {
                    accessFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
