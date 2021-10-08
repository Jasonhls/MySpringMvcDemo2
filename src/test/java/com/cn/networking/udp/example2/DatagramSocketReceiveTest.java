package com.cn.networking.udp.example2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/10/8 13:49
 * @Description:
 */
public class DatagramSocketReceiveTest {
    public static void main(String[] args) throws IOException {
        /**
         * 接受数据
         */
        DatagramSocket datagramSocket = new DatagramSocket(9000);
        byte[] inBuffer = new byte[1024];
        //接受数据的时候，不需要指定ip和端口
        DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        datagramSocket.receive(inPacket);
        String str = new String(inBuffer, 0, inPacket.getLength());
        System.out.println("服务端收到来自客户端发送的数据：" + str);

        /**
         * 发送数据给客户端
         */
        String response = "你好，我是服务端";
        byte[] outBuffer = response.getBytes(StandardCharsets.UTF_8);
        //注意：这里需要指定SocketAddress，根据原来接受数据的DatagramPacket去获取
        DatagramPacket outPacket = new DatagramPacket(outBuffer, outBuffer.length, inPacket.getSocketAddress());
        datagramSocket.send(outPacket);

        //关闭资源
        datagramSocket.close();

    }
}