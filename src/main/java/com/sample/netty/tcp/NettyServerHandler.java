package com.sample.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义handle ,需要继续netty规定好的某个HandlerAdapter
 *
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/26 09:40
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取客户端发送的数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("----ctx channel:{}", ctx.channel());
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline();
        //将msg转成一个ByteBuf
        log.info("msg:{}", msg);
        ByteBuf buf = (ByteBuf) msg;
        log.info("---msg:{}", buf.toString(CharsetUtil.UTF_8));
    }

    /**
     * 数据读取完毕
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存，并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好啊，我是小喵～", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常处理，关闭通道
        ctx.close();
    }
}
