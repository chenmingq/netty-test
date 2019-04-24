package com.netty.test.utils;

import com.netty.test.proto.BaseMsg;
import com.netty.test.proto.MessageProcessHelper;

public class MessageUtil {

    /**
     * 发送单个消息
     * @param baseMsg
     */
    public static void sendMsg (BaseMsg baseMsg){
        MessageProcessHelper.getInstance().responseExecute(baseMsg);
    }
}
