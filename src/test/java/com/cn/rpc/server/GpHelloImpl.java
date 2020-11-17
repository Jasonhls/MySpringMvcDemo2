package com.cn.rpc.server;

import com.cn.rpc.annotation.RpcAnnotation;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:34
 **/
@RpcAnnotation(value = IGpHello.class)
public class GpHelloImpl implements IGpHello{
    @Override
    public String sayHello(String name) {
        return "say hello to " + name;
    }
}
