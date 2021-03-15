package com.cn.socketAndNetty.netty.application.dispatcher;

import com.cn.socketAndNetty.netty.application.handler.HeartBeatMsgHandler;
import com.cn.socketAndNetty.netty.application.handler.WelcomeMsgHandler;
import com.cn.socketAndNetty.netty.application.wrapper.ChannelHandlerContextWrapper;
import com.cn.socketAndNetty.netty.application.Msg;
import com.cn.socketAndNetty.netty.application.MsgQueue;
import com.cn.socketAndNetty.netty.application.handler.IMsgHandler;
import com.cn.socketAndNetty.netty.application.handler.LoginMsgHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 16:24
 **/
public class MsgDispatcher {
    private static Map<Integer, Class<? extends IMsgHandler>> handlerMap;

    static {
        handlerMap = new HashMap<>();
        handlerMap.put(Msg.TYPE_LOGIN, LoginMsgHandler.class);
        handlerMap.put(Msg.TYPE_WELCOME, WelcomeMsgHandler.class);
        handlerMap.put(Msg.TYPE_HEART_BEAT, HeartBeatMsgHandler.class);
    }

    /**
     * 客户端的分发器
     */
    public static void clientDispatch() {
        if(MsgQueue.getInstance().canUse()) {
            Msg msg = MsgQueue.getInstance().next();
            if(msg == null) {
                return;
            }
            clientDispatcher(msg);
        }
    }

    public static void clientDispatcher(Msg msg) {
        try {
            IMsgHandler clientDispatcher = (IMsgHandler)Class.forName(handlerMap.get(msg.type).getName()).newInstance();
            clientDispatcher.clientHandle(msg);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 服务端的分发器
     * @param wrapper
     * @param msg
     */
    public static void serverDispatcher(ChannelHandlerContextWrapper wrapper, Msg msg) {
        try {
            IMsgHandler serverHandler = (IMsgHandler) Class.forName(handlerMap.get(msg.type).getName()).newInstance();
            serverHandler.serverHandle(wrapper, msg);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
