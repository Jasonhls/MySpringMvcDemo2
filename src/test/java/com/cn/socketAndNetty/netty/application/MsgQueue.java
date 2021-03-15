package com.cn.socketAndNetty.netty.application;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 16:16
 **/
public class MsgQueue {
    private PriorityBlockingQueue<Msg> mQueue;
    private boolean using;

    public MsgQueue() {
        mQueue = new PriorityBlockingQueue<>(128, new Comparator<Msg>() {
            @Override
            public int compare(Msg o1, Msg o2) {
                int res = o2.priority - o1.priority;
                if(res == 0 && o1.time != o2.time) {
                    return (int) (o2.time - o1.time);
                }
                return res;
            }
        });
    }

    private static class MsgQueueInner {
        private static final MsgQueue INSTANCE = new MsgQueue();
    }

    public static MsgQueue getInstance() {
        return MsgQueueInner.INSTANCE;
    }

    /**
     * 将消息加入消息队列中
     */
    public void enqueueMsg(Msg msg) {
        mQueue.add(msg);
    }

    /**
     * 从队列中获取消息
     * @return
     */
    public synchronized Msg next() {
        if(using) {
            return null;
        }
        Msg msg = mQueue.poll();
        if(msg != null) {
            makeUse(true);
        }
        return msg;
    }

    /**
     * 标记使用状态
     * @param use
     */
    public synchronized void makeUse(boolean use) {
        this.using = use;
    }

    /**
     * 是否能用
     * @return
     */
    public synchronized  boolean canUse() {
        return !using;
    }
}
