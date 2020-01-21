package com.pt.nio;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author :pt
 * @Date :Created in 9:44 2020/1/20
 * 测试文件通道读取本地文件
 */
public class TestFileChannelRead {

    public static void main(String[] args) throws IOException {
        //从文件里面读取内容
        File file=new File("E:\\testFile.txt");
        //获取文件输入流进行读取文件
        FileInputStream fs=new FileInputStream(file);
        //通过输入流对象获取文件channel
        FileChannel fileChannel = fs.getChannel();
        //创建一个字节缓冲区
        ByteBuffer byteBuffer=ByteBuffer.allocate((int)file.length());
        //将通道的数据读取到缓冲区中，放回为一个int，当int 的值为-1的时候表示读取完成
        fileChannel.read(byteBuffer);
        //将字节转换城字符串
        System.out.println(new String(byteBuffer.array()));
        fs.close();
    }
}
