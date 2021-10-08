package com.cn.networking.urlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: helisen
 * @Date 2021/10/8 14:58
 * @Description:
 */
public class UrlConnectionGetTest {
    public static void main(String[] args) throws IOException {
        /**
         * get请求
         */
        URL url = new URL("http://localhost:8080/hello");
        URLConnection urlConnection = url.openConnection();
        InputStream in = urlConnection.getInputStream();

        byte[] arr = new byte[1024];
        int len;
        while((len = in.read(arr)) != -1) {
            System.out.println(new String(arr, 0, len));
        }

        in.close();
    }
}
