package com.cn.socket.fileUpload;

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
            Socket socket = serverSocket.accept();
            new Thread(new Uploader(socket)).start();
        }
    }

}
