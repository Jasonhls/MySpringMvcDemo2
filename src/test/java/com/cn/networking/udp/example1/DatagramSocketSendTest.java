package com.cn.networking.udp.example1;

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
        byte[] buffer = content.getBytes(StandardCharsets.UTF_8);
        InetAddress receiverAddress = InetAddress.getLocalHost();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress, 9000);
        datagramSocket.send(packet);
    }
}
