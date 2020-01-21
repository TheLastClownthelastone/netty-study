package com.pt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Author :pt
 * @Date :Created in 11:23 2020/1/20
 * Scattering:将数据写入到buffer时，可以采用buffer数组，依次写入（分散）
 * Gathering：从buffer读取数据时候，可以采用buffer数组，依次读
 */
public class TestScatteringAndGatering {
    public static void main(String[] args) throws IOException {
        //使用ServerSocketChannel 和SocketChannel 网络
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建一个buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0]=ByteBuffer.allocate(5);
        byteBuffers[1]=ByteBuffer.allocate(3);

        //等待客户端连接
        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLenth=8; //假定从客户端接收8个字节
        //循环读取
        while (true){
            int byteRead=0;
            while (byteRead<messageLenth){
                long read = socketChannel.read(byteBuffers);
                byteRead+=read;//累计读取的字节数
                System.out.println("byread="+byteRead);
                //使用流打印 当前buffer的potion和limit
                Arrays.asList(byteBuffers).stream().map(byteBuffer -> "postion="+byteBuffer.position()+"，limit="+byteBuffer.limit()).forEach(System.out::println);
            }
            //将所有的buffer进行反转
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());
            //将数据读出显示到客户端
            long byteWirte=0;
            while (byteWirte<messageLenth){
                long write = socketChannel.write(byteBuffers);
                byteWirte+=write;
            }
            //将所有的buffer进行复位
            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.clear());
            System.out.println("byteRead="+byteRead+",byteWrite="+byteWirte+",messageLenth="+messageLenth);
        }
    }
}
