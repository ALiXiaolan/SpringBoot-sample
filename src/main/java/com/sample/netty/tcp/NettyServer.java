package com.sample.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/26 09:17
 */
@Slf4j
public class NettyServer {
    // 线程组 只负责处理连接请求
    private EventLoopGroup bossGroup;
    // 线程组 负责业务处理
    private EventLoopGroup workerGroup;

    public NettyServer() {
        bossGroup = new NioEventLoopGroup(1);
        // 默认线程数为 CPU核数*2
        workerGroup = new NioEventLoopGroup();
    }

    public void start() {
        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用链式编程进行设置
            bootstrap.group(bossGroup, workerGroup)
                    // 使用 NioServerSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    //设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给 pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    });
            log.info("Netty Server is starting...");
            //绑定一个端口并同步，生成一个ChannelFuture对象
            //启动服务器（并绑定端口）
            ChannelFuture cf = bootstrap.bind(6688).sync();

            //给cf注册监听器，监控我们关心的事件
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (cf.isSuccess()) {
                        log.info("监听端口6668成功");
                    }else {
                        log.info("监听端口6668失败");
                    }
                }
            });
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            //优雅的关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
