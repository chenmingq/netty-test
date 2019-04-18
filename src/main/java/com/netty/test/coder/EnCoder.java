package com.netty.test.coder;

import com.netty.test.proto.Message;
import com.netty.test.proto.CommonConst;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */

public class EnCoder extends MessageToByteEncoder<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message msg, ByteBuf out) throws Exception {
        doEncodeRequest(msg, out);
    }

    private void doEncodeRequest(Message request, ByteBuf out) {

        int magic = request.getMagic();
        byte[] body = request.getBody();
        int moduleId = request.getModuleId();
        long timestamp = request.getTimestamp();

        int length = CommonConst.HEAD_LENGTH + body.length;

        out.writeInt(magic)
                .writeInt(moduleId)
                .writeInt(length)
                .writeLong(timestamp)
                .writeBytes(body);

    }

}
