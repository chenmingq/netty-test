package com.netty.test.server;

import com.netty.test.Message;
import com.netty.test.pojo.db.DbParam;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.Data;


/**
 * @author : chenmq
 * date : 2019-4-09
 * Project : netty-test
 * Description： netty session
 */
@Data
public class Session {

    private Channel channel;

    private ChannelFuture channelFuture;

    private DbParam dbParam;

    public void sendMsg(Message msg) {
        channel.writeAndFlush(msg);
    }

    public ChannelFuture close() {
        this.channelFuture = this.channel.close();
        return this.channelFuture;
    }

    public static void main(String[] args) {
    }
}
