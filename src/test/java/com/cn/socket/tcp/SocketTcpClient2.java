package com.cn.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-25 10:26
 **/
public class SocketTcpClient2 {
	public static void main(String[] args) throws IOException {
		//创建socket
		final Socket socket = new Socket();
		//创建socket地址
		SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
		socket.connect(address);
		//向服务器发送的内容
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		String sendStr = "客户端问服务器端：你们在吗";
		out.write(sendStr);
		out.flush();
		//等待服务器端响应
		System.out.println("客户端已经发送了消息");
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String s = in.readLine();
		System.out.println("服务器端回复：" + s);
		//关闭连接
		in.close();
		out.close();
		socket.close();
	}
}
