package com.cn.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-25 10:26
 **/
public class SocketTcpServer {
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
		//获取OutputStream流
		OutputStream os = socket.getOutputStream();
		InputStream is = socket.getInputStream();
		byte[] bytes = new byte[1024];
		int len;
		if((len= is.read(bytes)) != -1) {
			System.out.println("服务器端接受到客户端消息：" + new String(bytes, 0, len));
		}
		//服务器端响应消息
		String sendStr = "我在";
		os.write(sendStr.getBytes());
		os.flush();

		//关闭所有连接
		os.close();
		is.close();
		socket.close();
		serverSocket.close();

	}
}
