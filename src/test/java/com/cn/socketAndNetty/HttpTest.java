package com.cn.socketAndNetty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-25 10:17
 **/
public class HttpTest {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8888);
		//一直监听，直到受到停止的命令
		while(true) {
			Socket socket = null;
			try {
				//如果没有请求，会一直hold在这里等待，有客户端请求的时候才会继续往下执行
				socket = serverSocket.accept();
				//获取输入流(请求)
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = null;
				//得到请求的内容，注意这里作两个判断，非空和""都要，只作非空判断会有问题
				while((line = bufferedReader.readLine()) != null && !line.equals("")) {
					sb.append(line).append("<br>");
				}
				String result = sb.toString();
				System.out.println(result);
				//这里第二个参数表示自动刷新缓存
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
				printWriter.println("HTTP/1.1 200 OK");
				printWriter.println("Content-Type:text/html;charset=utf-8");
				printWriter.println();

				printWriter.println("<h5>:你刚才发送的请求数据是：<br>");
				//将日志输出到浏览器
				printWriter.write(result);
				printWriter.println("</h5>");
				//release
				printWriter.close();
				bufferedReader.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
