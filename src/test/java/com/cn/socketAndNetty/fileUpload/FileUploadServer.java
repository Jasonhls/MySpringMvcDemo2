package com.cn.socketAndNetty.fileUpload;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 10:21
 **/
public class FileUploadServer {
    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(8888);
        while(true) {
            Socket socket = serverSocket.accept();//main启动后，程序会阻塞在这里，知道有客户端发来请求的数据，才会往下继续执行
            new Thread(new Uploader(socket)).start();
        }
    }

}
