package com.cn.socket.nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * 一、通道（Channel）：用于原节点与目标节点的连接。在Java NIO中负责缓冲区中数据的传输。
 * Channel本身不存储数据，需要配合缓冲区buffer进行传输。
 * <p>
 * 二、通道的主要实现类
 * Java.nio.channels.Channel 接口
 * |--FileChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 * 三、获取通道
 * 1.Java 针对支持通道的类提供了getChannel()方法
 * 本地IO:
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * <p>
 * 网络IO：
 * Socket
 * ServerSocket
 * DatagramSocket
 * <p>
 * 2.在JDK 1.7中NIO.2 针对各个通道提供了静态方法 open()
 * <p>
 * 3.在JDK1.7中NIO.2的Files 工具类的 newByteChannel()
 * <p>
 * 四、.通道之间的数据传输
 * transferFrom()
 * transferTo()
 * <p>
 * 五、分散（Scatter）与聚集（Gather）
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gather Writers）：将多个缓冲区中的数据聚集到一个通道中
 *
 * 六、字符集：Charsert
 * 编码：字符串-> 字节数组
 * 解码：字节数组-> 字符串
 *
 * @author: helisen
 * @create: 2020-10-19 15:15
 **/
public class TestChannel {
    @Test
    public void test() throws CharacterCodingException {
        Charset cs1 = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();
        //获取解码器
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer buf = CharBuffer.allocate(1024);
        buf.put("架构师之路");
        buf.flip();

        //编码
        ByteBuffer bBuf = ce.encode(buf);
        for (int  i = 0; i < bBuf.limit(); i++) {
            System.out.println(bBuf.get());
        }
        bBuf.flip();
        //解码
        CharBuffer cBuf = cd.decode(bBuf);
        System.out.println(cBuf.toString());
    }

    /**
     * 查看提供了哪些编码解码类型
     */
    @Test
    public void test2() {
        Map<String,Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> entries = map.entrySet();
        for (Map.Entry<String, Charset> entry : entries){
            System.out.println(entry.getKey() + "==" + entry.getValue());
        }
    }

    @Test
    public void test3() throws Exception {
        RandomAccessFile file = new RandomAccessFile("1.txt", "rw");
        //1.获取通道
        FileChannel channel = file.getChannel();
        //2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3.分散读取
        ByteBuffer[] bufs = new ByteBuffer[]{buf1, buf2};
        channel.read(bufs);
        for (ByteBuffer buffer : bufs) {
            buffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("------------------------------------------------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4.聚集写入
        RandomAccessFile file2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = file2.getChannel();
        channel2.write(bufs);

        channel.close();
        channel2.close();
    }

    /**
     * 通道之间的数据传输（直接缓冲区）
     */
    @Test
    public void test4() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("3.txt"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    /**
     * 快速直接从缓冲区完成文件的复制（内存映射文件）
     */
    @Test
    public void test5() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("美女1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("美女2.jpg"), StandardOpenOption.WRITE,
                StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inMapperBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMapperBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] dts = new byte[inMapperBuf.limit()];
        inMapperBuf.get(dts);
        outMapperBuf.put(dts);

        inChannel.close();
        outChannel.close();
    }

    /**
     * 利用通道完成文件复制（非直接缓冲区）
     */
    @Test
    public void test6() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("美女1.jpg");
            fos = new FileOutputStream("美女3.jpg");
            //获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);
            //将input通道的数据放入缓冲区
            while(inChannel.read(buf) != -1) {
                buf.flip();//切换读取数据的模式
                //将缓冲区的数据写入output通道
                outChannel.write(buf);
                buf.clear();//清空缓冲区
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
