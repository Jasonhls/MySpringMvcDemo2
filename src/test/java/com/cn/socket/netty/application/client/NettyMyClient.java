package com.cn.socket.netty.application.client;

import com.cn.socket.netty.application.callback.IConnectionCallback;
import com.cn.socket.netty.application.callback.IMsgCallback;
import com.cn.socket.netty.application.Msg;
import com.cn.socket.netty.demo.Config;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-21 16:30
 **/
public class NettyMyClient {

    private String mHost;
    private int mPort;
    private NettyMyClientHandler myClientHandler;
    private ChannelFuture channelFuture;
    private IConnectionCallback connectionCallback;

    //设置连接回调方法
    public void setCallback(IConnectionCallback callback) {
        this.connectionCallback = callback;
    }

    //设置消息回调，客户端收到消息，调用消息回调的方法
    public void registerMsgCallback(IMsgCallback callback) {
        if(myClientHandler == null) {
            throw new IllegalAccessError("please connect server first");
        }
        myClientHandler.registerMsgCallback(callback);
    }

    //发送消息
    public void sendMsg(Msg msg) {
        if(channelFuture == null || !channelFuture.isSuccess()) {
            throw new IllegalAccessError("please connect server first");
        }
        myClientHandler.sendMsg(msg);
    }

    public NettyMyClient(String mHost, int mPort) {
        this.mHost = mHost;
        this.mPort = mPort;
    }

    public void connection() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            myClientHandler = new NettyMyClientHandler();
            b.group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            /**
                             * 添加拆包器，需要在添加handler之前添加
                             */
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,
                                    Unpooled.copiedBuffer(Config.DATA_PACK_SEPARATOR.getBytes())));
                            /**
                             * 客户端配置心跳包
                             */
                            socketChannel.pipeline().addLast(new IdleStateHandler(60, 60, 10));
                            /**
                             * 配置编码器与解码器
                             * 配置了编码器与解码器之后，发送消息可以直接通过ctx.writeAndFlush("消息内容" + Config.DATA_PACK_SEPARATOR)的形式发送
                             */
                            socketChannel.pipeline().addLast("encoder", new StringEncoder());
                            socketChannel.pipeline().addLast("decoder", new StringDecoder());
                            /**
                             * 添加handler2
                             */
                            socketChannel.pipeline().addLast(myClientHandler);
                        }
                    });

            //start the dispatcher
            channelFuture = b.connect(mHost, mPort).sync();
            if(channelFuture.isSuccess()) {
                System.out.println("client，连接服务端成功");
                /**
                 * 连接服务端成功后，调用连接回调方法
                 */
                if(connectionCallback != null) {
                    connectionCallback.onConnected();
                }
            }

            // Wait until the connection is closed
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            group.shutdownGracefully();
        }
    }
}
