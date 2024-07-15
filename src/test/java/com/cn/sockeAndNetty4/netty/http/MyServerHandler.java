package com.cn.sockeAndNetty4.netty.http;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 何立森
 * @Date: 2024/07/12/15:56
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Logger logger = LoggerFactory.getLogger(MyServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        String uri = fullHttpRequest.uri();
        HttpMethod method = fullHttpRequest.method();
        String methodName = method.name();
        //根据路径，请求方式获取处理器
        HttpContainer.RequestHandler handler = HttpContainer.getHandler(uri, methodName);
        Method handlerMethod = handler.getMethod();
        Object object = handler.getObject();
        //处理请求参数
        Object[] requestParam = HttpContainer.handleRequestParam(fullHttpRequest, handlerMethod);
        //执行请求处理逻辑
        Object result = handlerMethod.invoke(object, requestParam);
        //处理请求结果
        handleResult(channelHandlerContext, handlerMethod, result);
    }

    private static void handleResult(ChannelHandlerContext channelHandlerContext, Method handlerMethod, Object result) {
        String json;
        AsciiString contentType;
        if(handlerMethod.isAnnotationPresent(ResponseBody.class)) {
            json = JSON.toJSONString(result);
            contentType = HttpHeaderValues.APPLICATION_JSON;
        }else {
            json = (String) result;
            contentType = HttpHeaderValues.FORM_DATA;
        }
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(json.getBytes(StandardCharsets.UTF_8))
        );
        fullHttpResponse.headers().add(HttpHeaderNames.CONTENT_TYPE, contentType);
        //这里一定要添加ChannelFutureListener.CLOSE这个结束监听器
        channelHandlerContext.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("处理http请求服务异常！");
        cause.printStackTrace();
    }
}
