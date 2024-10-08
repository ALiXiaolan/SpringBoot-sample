package com.sample.nio;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/20 16:32
 */
@Slf4j
public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6666;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    /**
     * 初始化
     */
    public GroupChatClient() throws IOException {
        selector = Selector.open();
        //连接服务器
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //将channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        //得到用户名
        username = socketChannel.getLocalAddress().toString().substring(1);
        log.info("用户：" + username + "连接成功...");
    }

    /**
     * 向服务器发送消息
     *
     * @param info
     */
    public void sendInfo(String info) {
        info = username + "说：" + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取从服务端回复的消息
     */
    public void readInfo() {
        StopWatch sw = new StopWatch();
        sw.start();
        sw.getTotalTimeMillis();
        try {
            //阻塞
            int readChannels = selector.select();
            if (readChannels > 0) {
                //有可用的通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        //获取通道
                        SocketChannel channel = (SocketChannel) key.channel();
                        //读取数据
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        //转换为字符串
                        String msg = new String(buffer.array());
                        log.info("回复的消息:{}", msg.trim());
                    }
                }
                //删除当前的selectionKey，防止重复操作
                iterator.remove();
            } else {
                log.info("没有可用的通道");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) throws IOException {
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();
        //启动一个线程，每个3秒，读取从服务器发送的数据
        new Thread() {
            public void run() {
                while (true) {
                    chatClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClient.sendInfo(msg);
        }

    }
}
