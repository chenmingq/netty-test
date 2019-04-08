package com.netty.test.server;

import com.netty.test.Header;
import com.netty.test.consts.CommonConst;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * channel 通道状态
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String str = "Welcome to " + InetAddress.getLocalHost().getHostName() + "！ It is  + new Date() +  now.\r\n";
        Header header = new Header();
        header.setBody(str.getBytes());
        header.setModuleId(1);
        header.setMagic(CommonConst.MAGIC_CODE);
        LOGGER.info("{} - > 加入", InetAddress.getLocalHost().getHostName());
        // Send greeting for a new connection.
        ctx.write(header);
        ctx.flush();
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
        if (o instanceof Header) {
            Header header = (Header) o;
            System.out.println(header);
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
