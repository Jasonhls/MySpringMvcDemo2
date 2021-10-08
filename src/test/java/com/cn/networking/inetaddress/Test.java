package com.cn.networking.inetaddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: helisen
 * @Date 2021/10/8 15:27
 * @Description:
 */
public class Test {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("www.baidu.com");
        System.out.println(address.getHostAddress());//14.215.177.39  或103.235.46.39
        System.out.println(address.getHostName());//www.baidu.com
        InetAddress address2 = InetAddress.getByName("baidu.com");
        System.out.println(address2.getHostAddress()); //220.181.38.148
        System.out.println(address2.getHostName()); //baidu.com

//        InetAddress address3 = InetAddress.getByName("14.215.177.39");
        InetAddress address3 = InetAddress.getByName("103.235.46.39");
        System.out.println(address3.getHostAddress());
        System.out.println(address3.getHostName());
    }
}
