package com.cn.socketAndNetty2.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 11:22
 **/
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel 组，管理所有的channel
    //每个客户端都有自己独立的handler，所以这里需要加static
    //GlobalEventExecutor.INSTANCE 是全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //handlerAdded 表示连接建立，一旦连接，第一个被执行
    //将当前channel加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其他在线的客户端
        /**
         * 该方法会将channelGroup中的 channel 遍历，并发送消息，也就是说给各个channel发送消息
         * 不需要自己再遍历
         */
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天 "+ sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
    }

    //端开连接，将 xx 客户离开信息推送给当前在线的客户
    //handlerRemove执行一次，会导致当前的channel自动从channelGroup中离开，不需要再手动移出
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //该方法会将channelGroup中的 channel 遍历，并发送消息，也就是说给各个channel发送消息
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
        System.out.println("channelGroup size " + channelGroup.size());
    }

    //表示channel 处于活动状态，提示 xx 上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了");
    }

    //表示 channel 处于不活动状态，提示 xx 离线了
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了");
    }

    //读取数据，并把数据转发给当前其他在线的客户端
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel
        Channel channel = ctx.channel();
        //遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            if(channel != ch) { //不是当前Channel，转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 发送了消息" + msg + "\n");
            }else {
                ch.writeAndFlush("[自己]发送了消息" + msg + "\n");//回显自己发送的消息给自己
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}
