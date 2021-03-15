package com.cn.socketAndNetty.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

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

		//4.使用Socket对象获取字节输入流对象
		InputStream is = socket.getInputStream();
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		while(!flag) {
			System.out.println("请输入传输内容：");
			String next = sc.next();

			//3.使用字节输出流对象的write方法，给服务器发送数据
			os.write(next.getBytes());
			os.flush();
			//5.使用字节输入流对象的read方法，读取服务器返回的数据
			byte[] bytes = new byte[1024];
			is.read(bytes);
			//打印出服务器返回的数据
			System.out.println("服务器返回的值为："+new String(bytes));
			while(true) {
				System.out.println("要继续传输内容吗？(Y/N)");
				String str = sc.next();
				if(str.equalsIgnoreCase("Y")) {
					break;
				}else if(str.equalsIgnoreCase("N")){
					flag = true;
					break;
				}
			}
		}
		System.out.println("释放资源了client");
		socket.close();
	}
}
