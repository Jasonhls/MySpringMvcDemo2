package com.cn.socket.fileUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 10:15
 **/
public class FileUploadClient {
    public static void main(String[] args) throws Exception{
        String filePath = "E:\\downloadTest\\123.jpg";
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        Socket socket = new Socket("localhost",8888);
        OutputStream os = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while((len = fis.read(bytes)) != -1) {
            os.write(bytes, 0, len);
        }
        //标记输出流到结尾
        socket.shutdownOutput();
        InputStream is = socket.getInputStream();
        byte[] newBytes = new byte[1024];
        int newLen;
        while((newLen = is.read(newBytes)) != -1) {
            System.out.println(new String(newBytes, 0, newLen));
        }
        socket.close();
        fis.close();
    }

}
