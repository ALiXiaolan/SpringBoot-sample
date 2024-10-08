package com.sample.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/26 14:56
 */
@Slf4j
public class NettyClient {

    public void start() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象，注意客户端使用的不是ServerBootstrap 而是Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            log.info("---client ok---");
            //启动客户端去连接服务器端
            ChannelFuture cf = bootstrap.connect("127.0.0.1", 6688).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            group.shutdownGracefully();
        }
    }
}
