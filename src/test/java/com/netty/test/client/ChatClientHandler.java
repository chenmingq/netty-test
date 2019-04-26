package com.netty.test.client;

import com.netty.test.proto.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author : chenmq
 * @date : 2019-3-17
 * Project : netty-wechat
 * Description：
 */
@ChannelHandler.Sharable
public class ChatClientHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * channel 通道状态
     *
     * @param ctx 52.63
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new Message());
    }


    /**
     * channel 读取消息
     *
     * @param ctx
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof Message) {
            Message message = (Message) o;
            byte[] body = message.getBody();
            System.out.println(message);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
