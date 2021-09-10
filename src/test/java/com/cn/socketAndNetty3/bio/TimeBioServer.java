package com.cn.socketAndNetty3.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: helisen
 * @Date 2021/8/31 16:02
 * @Description:
 */
public class TimeBioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        while(true) {
            Socket socket = server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader in = null;
                    PrintWriter out = null;
                    String expression;
                    String result;
                    try {
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out = new PrintWriter(socket.getOutputStream(), true);
                        while (true) {
                            if((expression = in.readLine()) != null) {
                                System.out.println("server----收到消息：" + expression);
                                result = expression;
                                out.println(result);
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        if(out != null) {
                            out.close();
                        }
                        if(in != null) {
                            try {
                                in.close();
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
            }).start();
        }
    }
}
