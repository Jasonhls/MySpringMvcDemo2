package com.cn.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-25 10:26
 **/
public class SocketTcpClient {
	public static void main(String[] args) throws IOException {
		//创建socket
		final Socket socket = new Socket();
		//创建socket地址
		SocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
		socket.connect(address);
		//向服务器发送的内容
		OutputStream os = socket.getOutputStream();
		String sendStr = "客户端问服务器端：你们在吗";
		os.write(sendStr.getBytes());
		os.flush();
		//等待服务器端响应
		InputStream is = socket.getInputStream();
		byte[] bytes = new byte[1024];
		int len;
		if((len = is.read(bytes)) != -1) {
			System.out.println("服务器端回复：" + new String(bytes, 0, len));
		}

		//关闭连接
		is.close();
		os.close();
		socket.close();
	}
}
