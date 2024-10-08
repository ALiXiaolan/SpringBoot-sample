package com.sample.nio;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xiaolan
 * @version 0.3.0-SNAPSHOT
 * @date 2024/9/11 14:58
 */
@Service
public class NioService {

    /**
     * 本地文件写数据
     */
    public static void writeToLocalFile() {
        try {
            String str = "write str to local file";
            //创建一个输出流
            FileOutputStream fileOutputStream = new FileOutputStream("test.txt");
            //通过fileOutputStream获取FileChannel
            FileChannel channel = fileOutputStream.getChannel();
            //创建一个缓冲区
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //将str放入缓冲区
            allocate.put(str.getBytes());
            //对缓冲区进行flip操作
            allocate.flip();
            //将byteBuffer中的数据写入到channel
            channel.write(allocate);
            //关闭流
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从本地文件读数据
     */
    public static String readFromLocalFile() {
        try {
            //创建文件的输入流
            File file = new File("test.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            //通过fileInputStream获取FileChannel
            FileChannel channel = fileInputStream.getChannel();
            //创建一个缓冲区
            ByteBuffer allocate = ByteBuffer.allocate((int) file.length());
            //将channel中的数据写入到缓冲区
            channel.read(allocate);
            //关闭流
            fileInputStream.close();
            //将byteBuffer的字节数据转成String
            return new String(allocate.array());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
