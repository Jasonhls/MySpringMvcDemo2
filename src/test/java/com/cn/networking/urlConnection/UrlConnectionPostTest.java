package com.cn.networking.urlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * @Author: helisen
 * @Date 2021/10/8 14:58
 * @Description:
 */
public class UrlConnectionPostTest {
    public static void main(String[] args) throws IOException {
        /**
         * Post请求
         */
        URL url = new URL("http://localhost:8080/echo");
        URLConnection urlConnection = url.openConnection();
        //URLConnection默认发送get请求，如果想发送POST请求，添加如下设置
        urlConnection.setDoOutput(true);
        //通过OutputStream向body里面写入数据
        OutputStream out = urlConnection.getOutputStream();
        out.write(("你好吗？我很好").getBytes(StandardCharsets.UTF_8));

        InputStream in = urlConnection.getInputStream();

        byte[] arr = new byte[1024];
        int len;
        while((len = in.read(arr)) != -1) {
            System.out.println(new String(arr, 0, len));
        }

        in.close();
    }
}
