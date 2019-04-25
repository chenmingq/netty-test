package com.netty.test.server;

import com.netty.test.utils.RemotingUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： netty端口服务启动
 */

@Slf4j
public class Server {

    private static ServerSocketChannel serverSocketChannel;

    private static ExecutorService publicExecutor;

    static {
        publicExecutor = Executors.newFixedThreadPool(4, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "NettyMainServer-" + this.threadIndex.incrementAndGet());
            }
        });
    }

    /**
     * 初始化服务
     */
    public static void initServer() {
        publicExecutor.execute(Server::init);
//        publicExecutor.execute(Server::oldInitServer);
    }


    private static boolean useEpoll() {
        return RemotingUtil.isLinuxPlatform()
                && Epoll.isAvailable();
    }

    private static void init() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                //保持连接数
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                //保持连接
                .option(ChannelOption.SO_KEEPALIVE, false)
                //有数据立即发送
                .childOption(ChannelOption.TCP_NODELAY, true)

                .childOption(ChannelOption.SO_SNDBUF, 65535)
                .childOption(ChannelOption.SO_RCVBUF, 65535)
                .localAddress(new InetSocketAddress(ServerProperties.PORT))
                .childHandler(new ServerInitializer());

        serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            boolean success = sync.isSuccess();
            InetSocketAddress addr = (InetSocketAddress) sync.channel().localAddress();
            if (success) {
                log.info("服务启动成功 -> {}", addr);
            } else {
                log.info("服务启动失败 -> {}", addr);
            }
            //等待服务监听端口关闭,就是由于这里会将线程阻塞,导致无法发送信息
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private static void oldInitServer() {
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
    }

}
