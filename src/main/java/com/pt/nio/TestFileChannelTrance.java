package com.pt.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @Author :pt
 * @Date :Created in 10:28 2020/1/20
 */
public class TestFileChannelTrance {
    public static void main(String[] args) throws IOException {
        //获取流，获取通道
        FileInputStream fi=new FileInputStream("a.jpg");
        FileChannel channel = fi.getChannel();
        FileOutputStream fo=new FileOutputStream("b.jpg");
        FileChannel channel1 = fo.getChannel();

        //使用transferFrom 进行copy 可以从一个通道中的内容拷贝到另外一个中
        //使用方法，那个通道需要拷贝，那个通道调用该方法，参数拷贝对象的channel ，起始位置，结束位置
        channel1.transferFrom(channel,0,channel.size());

        //关闭流
        fi.close();
        fo.close();
        channel.close();
        channel1.close();
    }
}
