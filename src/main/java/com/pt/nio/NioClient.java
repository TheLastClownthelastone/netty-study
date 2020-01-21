package com.pt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author :pt
 * @Date :Created in 16:19 2020/1/20
 */
public class NioClient {
    public static void main(String[] args) throws Exception {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞模式
        socketChannel.configureBlocking(false);
        //提供服务器端的ip 和端口
        InetSocketAddress localhost = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if(socketChannel.connect(localhost)){
            while (socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }
        //如果连接成功，发送数据
        String str="hello pt!";
        //可以根据字节数组的大小创建一个缓冲器
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将buffer数据写入channel中
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
