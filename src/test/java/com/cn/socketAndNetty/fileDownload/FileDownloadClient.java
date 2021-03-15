package com.cn.socketAndNetty.fileDownload;

import java.io.*;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 13:51
 **/
public class FileDownloadClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
        String filePath = "E:\\downloadTest\\new123.jpg";
        File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int len;
        while((len = is.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        os.write("下载完成".getBytes());
        socket.close();
        fos.close();
    }
}
