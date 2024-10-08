package com.sample.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/20 14:49
 */
@Slf4j
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;

    private static final int PORT = 6666;

    public GroupChatServer() {
        //初始化
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 监听
     */
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //监听通道
                        if (key.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            log.info("客户端连接成功，ip为：{}", socketChannel.getRemoteAddress());
                        }
                        //通道发送read事件，即通道是可读的状态
                        if (key.isReadable()) {
                            //处理读数据
                            readData(key);
                        }
                        //删除当前key，防止重复处理
                        iterator.remove();
                    }
                } else {
                    log.info("等待连接中...");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 处理读取数据
     *
     * @param key
     */
    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count值做处理
            if (count > 0) {
                //把缓冲区的数据转换为字符串
                String msg = new String(buffer.array());
                log.info("from 客户端：{}，内容：{}", channel.getRemoteAddress(), msg);
                // 向其他的客户转发消息（排除自己）
                sendMsgToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                log.error(channel.getRemoteAddress() + "离线了");
                //关闭通道
                key.cancel();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 转发消息给其他账号
     *
     * @param msg
     * @param self
     */
    private void sendMsgToOtherClients(String msg, SocketChannel self) {
        log.info("---消息转发中---");
        try {
            for (SelectionKey key : selector.keys()) {
                //通过key取出对应的通道,排除自己
                SelectableChannel channel = key.channel();
                if (channel instanceof SocketChannel && channel != self) {
                    SocketChannel dest = (SocketChannel) channel;
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    dest.write(buffer);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //创建服务端
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
