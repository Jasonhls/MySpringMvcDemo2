package com.cn.socket.netty.application.callback;

import com.cn.socket.netty.application.Msg;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 16:06
 **/
public interface IMsgCallback {
    /**
     * 接受到消息的回调
     * @param msg
     */
    void onMsgReceived(Msg msg);
}
