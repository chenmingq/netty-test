package com.netty.test.coder.msg;

import com.netty.test.proto.BaseMsg;

import java.util.Map;

public interface MsgCoder {
    Map<String,Object> prossonRequest(int cmdId,byte[] body);

    void prossonResponse (BaseMsg message);
}
