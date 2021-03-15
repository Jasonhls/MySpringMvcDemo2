package com.cn.socketAndNetty.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 00:14
 **/
public class SocketServer {
	public static void main(String[] args) throws IOException {
		//1.创建服务器ServerSocket对象和系统要指定的端口号
		ServerSocket serverSocket = new ServerSocket(8888);
		//2.使用ServerSocket对象中的方法accept，获取到请求的客户端对象Socket
		Socket socket = serverSocket.accept();
		//3.使用Socket对象获取字节输入流
		InputStream is = socket.getInputStream();
		//4.使用字节输入流对象的方法read，读取客户端发送的数据
		byte[] bytes = new byte[1024];
		int len;
		//5.使用Socket对象获取字节输出流
		OutputStream os = socket.getOutputStream();
		while((len = is.read(bytes)) > 0) {
			//打印出客户端请求的参数
			String data = new String(bytes, 0, len);
			System.out.println("客户端请求的数据为：" + data);
			//6.使用字节输出流对象的方法write，给客户端回写数据
			os.write("收到，谢谢，大家吃晚饭了吗？我好饿呀，真的太饿了".getBytes());
		}
		System.out.println("释放资源了");
		//7.释放资源
		socket.close();
		serverSocket.close();
	}
}
