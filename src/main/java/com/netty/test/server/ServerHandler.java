package com.netty.test.server;

import com.netty.test.common.RemotingHelper;
import com.netty.test.common.manager.SessionManager;
import com.netty.test.proto.CommonConst;
import com.netty.test.proto.Message;
import com.netty.test.proto.MessageProcessHelper;
import com.netty.test.utils.RemotingUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Date;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
@Slf4j
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
        Channel channel = ctx.channel();
        String channelRemoteAddr = RemotingHelper.parseChannelRemoteAddr(channel);
        LOGGER.info("NETTY SERVER PIPELINE: channelActive, the channel[{}]", channelRemoteAddr);
        Session session = new Session();
        session.setChannel(channel);
        SessionManager.getInstance().putSession(channelRemoteAddr, session);

        String str = "Welcome to " + InetAddress.getLocalHost().getHostName() + "！\r\n It is " + new Date() + " now.\r\n";
        Message message = new Message();
        message.setBody(str.getBytes());
        message.setModuleId(103);
        message.setCmdId(1);
        message.setMagic(CommonConst.MAGIC_CODE);
        ctx.write(message);
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
        boolean b = o instanceof Message;
        if (!b) {
            return;
        }
        Message message = (Message) o;
        System.out.println(message);
        MessageProcessHelper.getInstance().requestExecute(message);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.ALL_IDLE)) {
                final String remoteAddress = RemotingHelper.parseChannelRemoteAddr(ctx.channel());
                log.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                RemotingUtil.closeChannel(ctx.channel());
            }
        }

        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
