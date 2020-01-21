package com.pt.nio;

import sun.nio.ch.FileChannelImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author :pt
 * @Date :Created in 17:33 2020/1/19
 * 通道主要用来读写数据，通道来源于基本的Io流，
 * 而且通道进行不同的操作的时候要进行切换模式通过flip（）方法，可以将读模式切换成写模式
 * 通道的读写入方法需要传递参数为一个缓冲区
 */
public class TestFileChannel {
    public static void main(String[] args) throws IOException {
        String str="hello pt!";
        //创建一个输出流
        FileOutputStream fs=new FileOutputStream("E:\\testFile.txt");
        //通过输出流获取文件channel
        FileChannel fileChannel = fs.getChannel();
        //创建一个缓冲区，单列模式构造方法私有化，通过allocate实例
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //数据先写入缓冲区，再到filechannel
        byteBuffer.put(str.getBytes());
        //进行反转，进行读取（可以将缓存的操作进行读写切换）
        byteBuffer.flip();
        //将bytebuffer中的数据写入到filechannel中,读取用read，写入用write
        fileChannel.write(byteBuffer);
        fs.close();
    }
}
