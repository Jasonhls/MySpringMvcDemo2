package com.cn.socketAndNetty2.netty.simple;

import io.netty.util.NettyRuntime;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-13 14:49
 **/
public class Test {
    public static void main(String[] args) {
        //获取当前机器的CPU的核数，比如我的双CPU6核
        System.out.println(NettyRuntime.availableProcessors());
    }
}
