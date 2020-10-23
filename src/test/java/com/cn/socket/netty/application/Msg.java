package com.cn.socket.netty.application;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-22 15:39
 **/
public class Msg {
    /**
     * 欢迎
     */
    public static final int TYPE_WELCOME = 0;
    /**
     * 心跳
     */
    public static final int TYPE_HEART_BEAT = 1;
    /**
     * 登录
     */
    public static final int TYPE_LOGIN = 2;
    public static final int TYPE_COMMAND_A = 3;
    public static final int TYPE_COMMAND_B = 4;
    public static final int TYPE_COMMAND_C = 5;
    public int type;
    public String msg;
    public  int priority;
    public long time;

    @Override
    public String toString() {
        return "Msg{" +
                "type=" + type +
                ", msg='" + msg + '\'' +
                ", priority=" + priority +
                ", time=" + time +
                '}';
    }

    public static class LoginRequestInfo {
        /**
         * 用户名
         */
        public String user;
        /**
         * 密码
         */
        public String pwd;

        @Override
        public String toString() {
            return "LoginRequestInfo{" +
                    "user='" + user + '\'' +
                    ", pwd='" + pwd + '\'' +
                    '}';
        }
    }

    public static class LoginResponseInfo {
        public static final int CODE_SUCCESS = 0;
        public static final int CODE_FAILED = 100;

        /**
         * 响应码
         */
        public int code;
        /**
         * 响应数据
         */
        public String data;

        public static class ResponseData {
            public String token;
        }

        @Override
        public String toString() {
            return "LoginResponseInfo{" +
                    "code=" + code +
                    ", data='" + data + '\'' +
                    '}';
        }
    }
}
