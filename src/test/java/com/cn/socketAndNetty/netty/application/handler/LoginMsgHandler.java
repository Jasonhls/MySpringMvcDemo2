package com.cn.socketAndNetty.netty.application.handler;

import com.alibaba.fastjson.JSON;
import com.cn.socketAndNetty.netty.application.wrapper.ChannelHandlerContextWrapper;
import com.cn.socketAndNetty.netty.application.Msg;
import com.cn.socketAndNetty.netty.application.util.MsgUtil;

import java.util.UUID;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 16:38
 **/
public class LoginMsgHandler extends BaseCommandHandler{
    @Override
    public void doClientHandle(Msg msg) {
        System.out.println("client handle login msg：" + msg.toString());
    }

    /**
     * 服务端响应登录请求信息
     * @param wrapper
     * @param msg
     */
    @Override
    public void serverHandle(ChannelHandlerContextWrapper wrapper, Msg msg) {
        System.out.println("server handle login msg：" + msg.toString());
        Msg responseMsg = new Msg();
        responseMsg.type = Msg.TYPE_LOGIN;
        Msg.LoginResponseInfo response = new Msg.LoginResponseInfo();
        response.code = Msg.LoginResponseInfo.CODE_SUCCESS;
        response.data = UUID.randomUUID().toString();
        responseMsg.msg = JSON.toJSONString(response);
        MsgUtil.sendMsg(wrapper, responseMsg);
    }
}
