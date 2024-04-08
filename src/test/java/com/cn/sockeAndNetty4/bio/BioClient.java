package com.cn.sockeAndNetty4.bio;

import java.io.*;
import java.net.Socket;

/**
 * @Author: 何立森
 * @Date: 2023/07/26/10:20
 * @Description: BIO客户端
 */
public class BioClient {
    public static void main(String[] args) throws IOException {
        System.out.println(">>>>>...BIO客户端启动...>>>>>");
        //1、创建Socket并根据IP地址与端口连接服务端
        Socket socket = new Socket("127.0.0.1", 8888);
        //2、从Socket对象中获取一个字节输出流并封装成输出对象
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        //3、通过输出对象往服务端传递信息
        printStream.println("Hello!我是熊猫~");  //----->发送消息给服务端
        //4、通过下述方法告诉服务端已经完成发送，接下来只接受消息
        socket.shutdownOutput();
        //5、从套接字中获取字节输入流并封装成输入对象
        InputStream inputStream = socket.getInputStream();
        BufferedReader readBuffer = new BufferedReader(new InputStreamReader(inputStream));
        //6、通过输入对象从Buffer读取信息
        String msg;
        //----->这里的readBuffer.readLine()方法会阻塞，如果服务端发送来消息就会继续执行，然后继续等待服务端发送消息，除非服务端关闭输出流，就会退出阻塞
        while((msg = readBuffer.readLine()) != null) {
            System.out.println("收到信息：" + msg);
        }
        //7、发送完后清空输出流中的信息
        printStream.flush();
        //8、使用完成后关闭流对象和套接字
        outputStream.close();
        inputStream.close();
        socket.close();
    }
}
