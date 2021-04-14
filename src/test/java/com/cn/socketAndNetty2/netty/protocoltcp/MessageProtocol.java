package com.cn.socketAndNetty2.netty.protocoltcp;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 18:13
 **/
//协议包
public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
