package com.netty.test.client;

import com.netty.test.coder.DeCoder;
import com.netty.test.coder.EnCoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author : chenmq
 * @date : 2019-3-17
 * Project : netty-wechat
 * Description： server端的处理器
 */
public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {




    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new EnCoder());
        pipeline.addLast(new DeCoder());
        pipeline.addLast(new ChatClientHandler());

    }
}
