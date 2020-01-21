package com.pt.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author :pt
 * @Date :Created in 10:54 2020/1/20
 * mapperByteBuffer
 * 1.可以让文件直接在内存（堆外）中修改，即操作系统不需要拷贝一次
 */
public class TestMapperBuffer {

    public static void main(String[] args) throws Exception {
        RandomAccessFile rw = new RandomAccessFile("1.txt", "rw");
        //获取对应的文件通道
        FileChannel fileChannel = rw.getChannel();
        /**
         * 参数1：使用什么模式
         * 参数2：起始位置（可以修改的）
         * 参数3：映射到内存中的大小，即将文件多少个字节映射到内存，可以直接修改的范围表示0-5,不包括，其中表示的是大小不是，修改的下标
         * 实际类型：DirectByteBuffer
         */
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte)'H');
        map.put(3,(byte)'9');
        map.put(5,(byte)'Y');
        System.out.println("修改成功");
        rw.close();

    }
}
