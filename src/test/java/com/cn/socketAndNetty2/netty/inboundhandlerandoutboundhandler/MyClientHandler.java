package com.cn.socketAndNetty2.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @description:
 * @author: helisen
 * @create: 2021-04-14 16:26
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("得到服务器的ip" + ctx.channel().remoteAddress());
        System.out.println("收到服务器消息=" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        //分析：
        //1、"abcdabcdabcdabcd" 是16个字节
        //2、该处理器的前一个handler是 MyLongToByteEncoder
        //3、MyLongToByteEncoder 父类 MessageToByteEncoder
        //4、MessageToByteEncoder的write方法有如下代码：
        /*if (this.acceptOutboundMessage(msg)) { //判断当前传输的数据类型是不是应该处理的类型，对于MyLongToByteEncoder这个编码器来说，只有传递Long类型的数据才会被编码。
            I cast = msg;
            buf = this.allocateBuffer(ctx, msg, this.preferDirect);

            try {
                this.encode(ctx, cast, buf);
            } finally {
                ReferenceCountUtil.release(msg);
            }

            if (buf.isReadable()) {
                ctx.write(buf, promise);
            } else {
                buf.release();
                ctx.write(Unpooled.EMPTY_BUFFER, promise);
            }

            buf = null;
        } else {
            ctx.write(msg, promise);
        }*/
        //5、因此编写Encoder 要注意传入的数据类型和处理的数据类型必须一致。
//        ctx.writeAndFlush(Unpooled.copiedBuffer("abcdabcdabcdabcd", CharsetUtil.UTF_8));
        ctx.writeAndFlush(123456L);
    }
}
