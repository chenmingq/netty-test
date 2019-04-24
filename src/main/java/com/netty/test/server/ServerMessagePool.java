package com.netty.test.server;

import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3.Builder;
import com.netty.test.pojo.proto.NettyTest;
import com.netty.test.proto.Message;
import com.netty.test.proto.MessageProcessHelper;

import java.util.HashMap;
import java.util.Map;

public class ServerMessagePool {

    private static ServerMessagePool instance = new ServerMessagePool();

    public static ServerMessagePool getInstance() {
        return instance;
    }

    public ServerMessagePool() {
    }

    private static Map<Integer, Descriptors.Descriptor> registerMsgPoolMap = new HashMap<>();

    /**
     * 消息注册
     */
    public void registerMsgProto() {
        registerMsgPoolMap.put(NettyTest.MESSAGE_TYPE.REQ_LOGIN_VALUE, NettyTest.ReqLogin.getDescriptor());
        registerMsgPoolMap.put(NettyTest.MESSAGE_TYPE.REQ_QUERY_TABLE_DATA_VALUE, NettyTest.ReqQueryTableData.getDescriptor());
    }

    /**
     * 获取注册的消息
     *
     * @param msgId
     * @return
     */
    public Descriptors.Descriptor getMsg(int msgId) {
        if (!registerMsgPoolMap.containsKey(msgId)) {
            return null;
        }
        return registerMsgPoolMap.get(msgId);
    }

    public static void main(String[] args) {

    }


}
