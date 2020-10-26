package com.cn.socket.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-25 10:26
 **/
public class SocketTcpServer2 {
	public static void main(String[] args) throws IOException {
		//创建Server Socket
		ServerSocket serverSocket = new ServerSocket();
		//创建我们的Socket监听连接地址和端口
		SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
		//绑定我们的监听地址
		serverSocket.bind(address);
		System.out.println("等待客户端发送消息。。。");
		//调用下面的方法的时候，当前程序就会实现阻塞
		Socket socket = serverSocket.accept();
		//读取客户端请求信息
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		char[] bytes = new char[1024];
		while(in.read(bytes) != -1) {
			System.out.println("服务器端接受到客户端消息：" + new String(bytes));
		}
		//服务器端响应消息
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		String sendStr = "我在";
		out.write(sendStr);
		out.flush();

		//关闭所有连接
		out.close();
		in.close();
		socket.close();
		serverSocket.close();

	}
}
