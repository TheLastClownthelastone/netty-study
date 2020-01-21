package com.pt.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author :pt
 * @Date :Created in 15:50 2020/1/20
 */
public class NioServer {
    public static void main(String[] args) throws Exception {
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();

        //得到一个selector
        Selector selector = Selector.open();
        System.out.println(selector);
        //绑定端口，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把ServerSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true){
            //等待1秒，如果1秒没有事件发生，返回
            if(selector.select(1000)==0){//没有事件发生
                System.out.println("服务器等待了1秒》》》无连接");
                continue;
            }
            //如果返回的大于0,获取到相关的selectionKey集合
            //1.如果返回大于0，表示已经获取到关注的事件了
            //2.selector.selectKeys() 返回事件的集合
            //3.通过selectKeys可以反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()){
                //获取selectKey
                SelectionKey key = keyIterator.next();
                //根据key对应的通道发生的事件做相应的处理
                if(key.isAcceptable()){//连接事件
                    //给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //设置socketChannel为非阻塞
                    socketChannel.configureBlocking(false);

                    //注册到selector上,关注事件为OP_READ时，同时给Channel关联一个buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()){//发生了OP_READ事件
                    //通过key反向获取channel

                    //获取到该channel关联的buffer

                    new Thread() {
                        @Override
                        public void run() {
                            SocketChannel channel =(SocketChannel) key.channel();
                            ByteBuffer buffer = (ByteBuffer) key.attachment();
                            try {
                                channel.read(buffer);
                                System.out.println("form 客户端"+new String(buffer.array()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                }

                //手动从集合中移动当前的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
