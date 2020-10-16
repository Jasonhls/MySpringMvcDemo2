package com.cn.socket.fileDownload;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 13:43
 **/
public class FileDownloadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        String downloadUrl = "E:\\uploadTest\\123.jpg";
        while(true) {
            Socket socket = serverSocket.accept();//启动main之后，当前线程会阻塞在这行代码这里，直到有收到客户端请求数据过来，才会往下执行
            new Thread(new Downloader(socket,downloadUrl)).start();
        }
    }
}
