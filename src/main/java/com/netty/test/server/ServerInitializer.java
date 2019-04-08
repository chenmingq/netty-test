package com.netty.test.server;

import com.netty.test.coder.DeCoder;
import com.netty.test.coder.EnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DeCoder());
        pipeline.addLast(new EnCoder());
        pipeline.addLast(new ServerHandler());

    }
}
