package com.cn.socketAndNetty.fileUpload;

import java.io.*;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 12:42
 **/
public class Uploader implements Runnable{
    String destinationPath = "E:\\uploadTest\\123.jpg";
    private Socket socket;

    private InputStream is = null;
    private OutputStream os = null;
    private FileOutputStream fos;

    public Uploader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            File file = new File(destinationPath);
            fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            while((len = is.read(bytes)) > 0) {
                fos.write(bytes, 0, len);
            }
            //把上传文件存储的url返回给客户端
            String url = destinationPath;
            os.write(url.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                        fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
