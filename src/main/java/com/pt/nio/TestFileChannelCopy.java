package com.pt.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author :pt
 * @Date :Created in 10:08 2020/1/20
 * 实现文件的拷贝，只能使用一个缓冲区
 */
public class TestFileChannelCopy {
    public static void main(String[] args) throws IOException {
        //进行文件的读取
        FileInputStream fs=new FileInputStream("1.txt");
        //得到文件通道
        FileChannel fileChannel = fs.getChannel();
        //获取byteBuffer缓冲区
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);

        FileOutputStream fileOutputStream=new FileOutputStream("2.txt");
        FileChannel channel = fileOutputStream.getChannel();


        //循环读取
        while(true){
            //清空byteBuffer，进行文件的复位
            byteBuffer.clear();

            int read = fileChannel.read(byteBuffer);
            if(read==-1){
                break;
            }
            //进行缓冲区的反转
            byteBuffer.flip();
            //将bytebuffer中的数据写入filechannel  文件输出中
            channel.write(byteBuffer);
        }
        //关闭流
        fs.close();
        fileOutputStream.close();
    }
}
