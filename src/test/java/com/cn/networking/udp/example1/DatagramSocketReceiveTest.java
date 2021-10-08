package com.cn.networking.udp.example1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Author: helisen
 * @Date 2021/10/8 13:49
 * @Description:
 */
public class DatagramSocketReceiveTest {
    public static void main(String[] args) throws IOException {
        //接受数据
        DatagramSocket datagramSocket = new DatagramSocket(9000);
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(packet);
        String str = new String(buffer, 0, packet.getLength());
        System.out.println(str);
    }
}
