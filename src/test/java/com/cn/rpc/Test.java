package com.cn.rpc;

import com.cn.rpc.server.registry.IRegisterCenter;
import com.cn.rpc.server.registry.RegisterCenterImpl;

import java.io.IOException;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 10:00
 **/
public class Test {
    public static void main(String[] args) throws IOException {
        IRegisterCenter iRegisterCenter = new RegisterCenterImpl();
        iRegisterCenter.register("com.cn.rpc.hls", "127.0.0.1:9090");
        System.in.read();
    }
}
