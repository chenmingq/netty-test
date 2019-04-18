package com.netty.test.coder;

import com.netty.test.proto.Message;
import com.netty.test.proto.CommonConst;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
public class DeCoder extends ReplayingDecoder<Message> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        int magic = in.readInt();

        if (magic != CommonConst.MAGIC_CODE) {
            return;
        }

        int moduleId = in.readInt();
        int length = in.readInt();
        long timestamp = in.readLong();
        int bodyLength = length - CommonConst.HEAD_LENGTH;

        if (in.readableBytes() < bodyLength) {
            return;
        }
        byte[] body = new byte[bodyLength];
        in.readBytes(body);

        Message message = new Message();
        message.setBody(body);
        message.setModuleId(moduleId);
        message.setTimestamp(timestamp);
        message.setMagic(magic);

        list.add(message);
    }
}
