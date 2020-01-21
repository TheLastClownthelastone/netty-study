package com.pt.nio;

import java.nio.IntBuffer;

/**
 * @Author :pt
 * @Date :Created in 16:17 2020/1/19
 * 缓冲区的作用，用来与通道进行双向通讯，和client 进行直接通讯，
 * 避免了client 直接与通道（channel）
 */
public class BasicBuffer {

    public static void main(String[] args) {
        //举例说明buffer的使用(简单说明)
        //创建一个buffer,大小为5，既可以存入5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        //向buffer中存放数据
        /*intBuffer.put(10);
        intBuffer.put(11);
        intBuffer.put(12);
        intBuffer.put(13);
        intBuffer.put(14);*/
        //获取buffer的容量
       /* int capacity = intBuffer.capacity();
        System.out.println(capacity);*/
        for(int i=0;i<intBuffer.capacity();i++){
            intBuffer.put(i*2);
        }

        //如何从buffer中读取数据(切换状态，从写入状态切换至读取状态)
        intBuffer.flip();
        //将buffer转换，读写切换
        while (intBuffer.hasRemaining()){
            //采用指针，进行调动
            System.out.println(intBuffer.get());
        }
        //buffer中postion作用表示当前的坐标索引位置

    }
}
