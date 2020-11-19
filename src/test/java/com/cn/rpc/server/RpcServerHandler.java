package com.cn.rpc.server;

import com.cn.rpc.bean.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 10:04
 **/
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获得客户端传输过来的数据
        RpcRequest request = (RpcRequest)msg;
        Object result = new Object();

        //服务名称 IGpHello
        if(handlerMap.containsKey(request.getClassName())) {
            Object obj = handlerMap.get(request.getClassName());
            Method method = obj.getClass().getMethod(request.getMethodName(), request.getTypes());
            //返回接口方法处理的结果
            result = method.invoke(obj, request.getParams());
        }
        //写给客户端result结果
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }
}
