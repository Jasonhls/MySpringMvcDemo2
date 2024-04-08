package com.cn.sockeAndNetty4.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: 何立森
 * @Date: 2023/07/26/10:07
 * @Description: BIO服务端
 */
public class BioServer {
    public static void main(String[] args) throws IOException {
        System.out.println(">>>>>...BIO服务端启动...>>>>>");
        //1、定义个ServerPort服务端对象，并为其绑定端口号
        ServerSocket server = new ServerSocket(8888);
        //2、监听客户端Socket连接
        Socket socket = server.accept(); //----->这里的server.accept()方法会阻塞，如果以后Socket监听到8888，这里就不会阻塞
        //3、从套接字中得到字节输入流并封装成输入流对象
        InputStream inputStream = socket.getInputStream();
        BufferedReader readBuffer = new BufferedReader(new InputStreamReader(inputStream));
        //4、从Buffer中读取信息，如果读到信息则输出
        String msg;
        //---->这里的readBuffer.readLine()方法会阻塞，如果有客户端发送来消息，就会继续执行，然后阻塞继续等待客户端发送消息，除非客户端关闭输出流，就会退出阻塞
        while((msg = readBuffer.readLine()) != null) {
            System.out.println("收到信息：" + msg);
        }
        //5、从套接字中获取字节输出流并封装成输出对象
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        //6、通过输出对象往服务端传递信息
        printStream.println("Hi!我是竹子~"); //----->发送消息给客户端
        //7、发送后清空输出流中的信息
        printStream.flush();
        //8、使用后关闭流对象与套接字
        outputStream.close();
        inputStream.close();
        socket.close();
        server.close();
    }

}
