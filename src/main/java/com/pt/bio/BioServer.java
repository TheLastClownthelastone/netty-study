package com.pt.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author :pt
 * @Date :Created in 15:29 2020/1/19
 * 测试java的bio模式，使用线程池机制
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        //线程池机制

        //1创建一个线程池
        //2如果有客户端连接，就创建一个线程与之通讯

        ExecutorService executorService= Executors.newCachedThreadPool();

        //创建一个Serversocket
        ServerSocket serverSocket=new ServerSocket(6666);

        System.out.println("服务器启动了》》》》");

        while (true){
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端了");

            //创建线程
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯的
                    handler(socket);
                }
            });

        }
    }

    /**
     * 编写一个客户端方法，可以客户端通讯
     */
    public static  void handler(Socket socket){
        try {
            System.out.println("线程id："+Thread.currentThread().getId()+"线程名字："+Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket 获取输入流
            InputStream inputStream = socket.getInputStream();

            //循环读取客户端发送的数据
            while (true){
                System.out.println("线程id："+Thread.currentThread().getId()+"线程名字："+Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if(read!=-1){
                    //继续读取
                    System.out.println(new String(bytes,"UTF-8"));
                }else {
                    //打破循环
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭客户端连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
