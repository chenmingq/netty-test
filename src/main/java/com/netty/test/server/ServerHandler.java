package com.netty.test.server;

import com.netty.test.common.RemotingHelper;
import com.netty.test.common.manager.SessionManager;
import com.netty.test.proto.Message;
import com.netty.test.proto.MessageProcessHelper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
      /*  String str = "Welcome to " + InetAddress.getLocalHost().getHostName() + "！ It is  + new Date() +  now.\r\n";
        Message message = new Message();
        message.setBody(str.getBytes());
        message.setModuleId(1);
        message.setMagic(CommonConst.MAGIC_CODE);
        LOGGER.info("{} - > 加入", InetAddress.getLocalHost().getHostName());
        // Send greeting for a new connection.
        ctx.write(message);
        ctx.flush();*/
        Channel channel = ctx.channel();
        String channelRemoteAddr = RemotingHelper.parseChannelRemoteAddr(channel);
        LOGGER.info("NETTY SERVER PIPELINE: channelActive, the channel[{}]", channelRemoteAddr);
        Session session = new Session();
        session.setChannel(channel);
        SessionManager.getInstance().putSession(channelRemoteAddr, session);

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
            System.out.println(message);
            MessageProcessHelper.getInstance().requestExecute(message);
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
