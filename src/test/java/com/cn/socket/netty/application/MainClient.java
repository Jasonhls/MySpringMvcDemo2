package com.cn.socket.netty.application;

import com.alibaba.fastjson.JSON;
import com.cn.socket.netty.application.callback.IConnectionCallback;
import com.cn.socket.netty.application.callback.IMsgCallback;
import com.cn.socket.netty.application.connection.ConnectionManager;
import com.cn.socket.netty.application.connection.NettyMyClientConnection;
import com.cn.socket.netty.application.dispatcher.MsgDispatcher;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-23 15:13
 **/
public class MainClient {
    public static void main(String[] args) {
        try{
            String host = "127.0.0.1";
            int port = 12345;
            //初始化NettyMyConnection
            ConnectionManager.initConnection(new NettyMyClientConnection());
            /**
             *获取ConnectionManager实例，并调用它的connect方法，会创建Netty客户端，同时添加连接回调connectionCallback
             */
            ConnectionManager.getInstance().connect(host, port, new IConnectionCallback() {
                @Override
                public void onConnected() {
                    System.out.println("MainClient的connectionCallback执行onConnected()方法");

                    /**
                     * 注册客户端的消息回调
                     */
                    ConnectionManager.getInstance().registerMsgCallback(new IMsgCallback() {
                        //客户端收到消息，就会回调这个方法
                        @Override
                        public void onMsgReceived(Msg msg) {
                            /**
                             * 客户端把收到的消息放入队列中
                             */
                            MsgQueue.getInstance().enqueueMsg(msg);
                            /**
                             * 客户端处理从队列中取出消息，并执行分发器逻辑
                             */
                            MsgDispatcher.clientDispatch();
                        }
                    });

                    /**
                     *客户端连接成功之后，会执行连接回调方法到这里，然后这里会向服务端发送登录请求
                     */
                    Msg msg = new Msg();
                    msg.type = Msg.TYPE_LOGIN;
                    Msg.LoginRequestInfo request = new Msg.LoginRequestInfo();
                    request.user = "hls";
                    request.pwd = "hls";
                    msg.msg = JSON.toJSONString(request);
                    ConnectionManager.getInstance().sendMsg(msg);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
