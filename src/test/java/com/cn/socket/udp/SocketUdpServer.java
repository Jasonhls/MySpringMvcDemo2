package com.cn.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-25 10:26
 **/
public class SocketUdpServer {
	public static void main(String[] args) throws IOException {
		/**
		 * 接受客户端发送的数据
		 */
		//1.创建服务器端DatagramSocket，指定端口
		DatagramSocket socket = new DatagramSocket(8800);
		//2.创建数据报，用于接受客户端发送的数据
		byte[] data = new byte[1024];
		//创建字节数组，指定接受的数据包的大小
		DatagramPacket packet = new DatagramPacket(data, data.length);
		//3.接受客户端发送的数据
		System.out.println("***服务器已经启动，等待客户端发送数据");
		//此方法在接受报之前会一直阻塞
		socket.receive(packet);
		//4.读取数据
		String info = new String(data, 0, packet.getLength());
		System.out.println("我是服务器，客户端说：" + info);

		/**
		 * 向客户端响应数据
		 */
		//1.创建数据报，用于响应客户端
		byte[] data2 = "你好，我是服务器端".getBytes();
		DatagramPacket packet2 =new DatagramPacket(data2, data2.length);
		//2.向客户端发送数据
		String sendStr = new String(data2, 0, data2.length);
		System.out.println("服务器端向客户端发送数据：" + sendStr);
		socket.send(packet2);
		//3.关闭资源
		socket.close();
	}
}
