package com.cn.socket;

import java.io.*;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-15 18:11
 **/
public class SingleServer implements Runnable{
    private Socket socket;
    private int clientNo;

    public SingleServer(Socket socket, int clientNo) {
        this.socket = socket;
        this.clientNo = clientNo;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            do {
                double length = dis.readDouble();
                System.out.println("收到客户端输入的值为：" + length);
                double result = length * length;
                dos.writeDouble(result);
                dos.flush();
            }while(dis.readInt() != 0);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("向客户端" + clientNo + "写数据结束");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
