package com.cn.socket.netty.application.wrapper;

import com.alibaba.fastjson.JSON;
import com.cn.socket.netty.demo.Config;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description: 定义一个ChannelHandlerContextWrapper统一管理消息分隔符
 * @author: helisen
 * @create: 2020-10-22 15:41
 **/
public class ChannelHandlerContextWrapper {
    private ChannelHandlerContext context;

    public ChannelHandlerContextWrapper(ChannelHandlerContext context) {
        this.context = context;
    }

    /**
     * 包装writeAndFlush方法
     * @param object
     */
    public void writeAndFlush(Object object) {
        context.writeAndFlush(JSON.toJSONString(object) + Config.DATA_PACK_SEPARATOR);
    }
}
