package com.netty.test.coder.msg;

import java.util.Map;

public interface MsgCoder {
    Map<String,Object> prosson(int cmdId,byte[] body);
}
