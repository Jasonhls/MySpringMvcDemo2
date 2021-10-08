package com.cn.networking.udp.example2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/10/8 13:42
 * @Description:
 */
public class DatagramSocketSendTest {
    public static void main(String[] args) throws IOException {
        //发送数据
        DatagramSocket datagramSocket = new DatagramSocket();
        String content = "哈哈，你吃过饭了没？";
        byte[] outBuffer = content.getBytes(StandardCharsets.UTF_8);
        InetAddress address = InetAddress.getLocalHost();
        //需要执行ip和端口
        DatagramPacket outPacket = new DatagramPacket(outBuffer, outBuffer.length, address, 9000);
        datagramSocket.send(outPacket);

        //接受来自服务端的响应
        byte[] inBuffer = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(inBuffer, inBuffer.length);
        datagramSocket.receive(inPacket);
        String response = new String(inBuffer, 0, inPacket.getLength());
        System.out.println("客户端收到来自服务端响应的数据：" + response);

        //关闭资源
        datagramSocket.close();
    }
}