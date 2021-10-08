package com.cn.networking.urlConnection;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * @Author: helisen
 * @Date 2021/10/8 14:58
 * @Description:
 */
public class UrlConnectionJarTest {
    public static void main(String[] args) throws IOException {
        /**
         * 读jar
         */
        URL jarUrl = new URL("file:/C:/Users/EDZ/Desktop/io_test/1.txt");
        JarURLConnection connection = new JarURLConnection(jarUrl) {
            @Override
            public JarFile getJarFile() throws IOException {
                return null;
            }

            @Override
            public void connect() throws IOException {

            }
        };
        Manifest manifest = connection.getManifest();
        JarFile jarFile = connection.getJarFile();
    }
}
