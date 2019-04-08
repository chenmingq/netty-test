package com.netty.test.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */

public class Server {

    private static ServerSocketChannel serverSocketChannel;


    /**
     * 初始化服务
     */
    public static void initServer() {
        new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        //保持连接数
                        .option(ChannelOption.SO_BACKLOG, 128)
                        //有数据立即发送
                        .option(ChannelOption.TCP_NODELAY, true)
                        //保持连接
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ServerInitializer());

                //绑定端口，同步等待成功
                ChannelFuture future;
                future = bootstrap.bind(ServerProperties.PORT).sync().channel().closeFuture().sync();

                if (future.isSuccess()) {
                    serverSocketChannel = (ServerSocketChannel) future.channel();
                    System.out.println("服务端开启成功");
                } else {
                    System.out.println("服务端开启失败");
                }

                //等待服务监听端口关闭,就是由于这里会将线程阻塞,导致无法发送信息
                future.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }).start();
    }
}
