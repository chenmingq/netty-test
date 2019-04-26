package com.netty.test.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author : chenmq
 * @date : 2019-3-17
 * Project : netty-wechat
 * Description：
 */
public class ClientStart {


    private static SocketChannel socketChannel;

    public static void main(String[] args) {
        new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        // 保持连接
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        // 有数据立即发送
                        .option(ChannelOption.TCP_NODELAY, true)
                        // 绑定处理group
                        .remoteAddress("localhost", 9901)
                        .handler(new ChatClientInitializer());

                // Make a new connection.
                ChannelFuture future = b.connect("localhost", 9901).sync();

                if (future.isSuccess()) {
                    // 得到管道，便于通信
                    socketChannel = (SocketChannel) future.channel();
                    System.out.println("客户端开启成功...");
                } else {
                    System.out.println("客户端开启失败...");
                }
                // 等待客户端链路关闭，就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
                future.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }).start();
    }


}
