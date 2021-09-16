package com.cn.socketAndNetty3.netty.time;

import java.util.Date;

/**
 * @Author: helisen
 * @Date 2021/9/16 11:41
 * @Description:
 */
public class UnixTime {
    private long value;

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTime(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}
