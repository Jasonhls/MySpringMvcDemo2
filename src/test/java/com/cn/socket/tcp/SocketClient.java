package com.cn.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 00:24
 **/
public class SocketClient {
	public static void main(String[] args) throws IOException {
		//1.创建一个客户端对象Socket，构造方法中指定ip和端口
		Socket socket = new Socket("localhost", 8888);
		//2.使用Socket对象获取字节输出流对象
		OutputStream os = socket.getOutputStream();
		//3.使用字节输出流对象的write方法，给服务器发送数据
		os.write("大家好".getBytes());
		//4.使用Socket对象获取字节输入流对象
		InputStream is = socket.getInputStream();
		//5.使用字节输入流对象的read方法，读取服务器返回的数据
		byte[] bytes = new byte[1024];
		int len;
		while((len = is.read(bytes)) != 0) {
			//打印出服务器返回的数据
			System.out.println(new String(bytes, 0, len));
		}
		socket.close();
	}
}
