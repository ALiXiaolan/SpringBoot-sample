package com.sample.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * SimpleChannelInboundHandler 是ChannelInboundHandler的子类，用于处理入站事件，并且可以自动释放资源
 * HttpObject客户端和服务器端相互通讯的数据被封装成httpObject
 *
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/29 15:24
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * 读取客户端数据
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        log.info("---channel:{},pipeline:{},pipeline 的 channel:{}", ctx.channel(), ctx.pipeline(), ctx.pipeline().channel());
        log.info("---handler:{}", ctx.handler());

        //判断msg是不是httpRequest请求
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            log.info("---request:{}", httpRequest.uri());
            //获取uri，过滤指定的资源
            URI uri = new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())) {
                log.info("请求了 favicon.ico, 不做响应");
                return;
            }
            //回复信息给浏览器
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            //构造一个http响应，即httpResponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

            ctx.writeAndFlush(response);
        }
    }
}
