package com.cn.socket.fileDownload;

import java.io.*;
import java.net.Socket;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-16 13:44
 **/
public class Downloader implements Runnable{
    private Socket socket;
    private String downloadUrl;

    private FileInputStream fis = null;
    private OutputStream os;

    public Downloader(Socket socket, String downloadUrl) {
        this.socket = socket;
        this.downloadUrl = downloadUrl;
    }

    @Override
    public void run() {
        try {
            File file = new File(downloadUrl);
            fis = new FileInputStream(file);
            os = socket.getOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while((len = fis.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
