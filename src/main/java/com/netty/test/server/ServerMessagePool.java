package com.netty.test.server;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.netty.test.pojo.proto.NettyTest;

import java.util.HashMap;
import java.util.Map;

public class ServerMessagePool {

    private static ServerMessagePool instance = new ServerMessagePool();

    public static ServerMessagePool getInstance() {
        return instance;
    }

    public ServerMessagePool() {
    }

    private static Map<Integer, Descriptors.Descriptor> registerRequestMsgPoolMap = new HashMap<>();
    private static Map<Integer, Message> registerResponseMsgPoolMap = new HashMap<>();

    /**
     * request消息注册
     */
    public void registerRequestMsgProto() {
        registerRequestMsgPoolMap.put(NettyTest.MESSAGE_TYPE.REQ_LOGIN_VALUE, NettyTest.ReqLogin.getDescriptor());
        registerRequestMsgPoolMap.put(NettyTest.MESSAGE_TYPE.REQ_QUERY_TABLE_DATA_VALUE, NettyTest.ReqQueryTableData.getDescriptor());
    }

    /**
     * response消息注册
     */
    public void registerResponseMsgProto() {
        registerResponseMsgPoolMap.put(NettyTest.MESSAGE_TYPE.RES_LOGIN_VALUE, NettyTest.ResLogin.getDefaultInstance());
        registerResponseMsgPoolMap.put(NettyTest.MESSAGE_TYPE.RES_QUERY_TABLE_DATA_VALUE, NettyTest.ResQueryTableData.getDefaultInstance());
    }

    /**
     * 获取request消息注册
     *
     * @param msgId
     * @return
     */
    public Descriptors.Descriptor getReqMsg(int msgId) {
        if (!registerRequestMsgPoolMap.containsKey(msgId)) {
            return null;
        }
        return registerRequestMsgPoolMap.get(msgId);
    }

    /**
     * response消息注册
     *
     * @param msgId
     * @return
     */
    public Message getResMsg(int msgId) {
        if (!registerResponseMsgPoolMap.containsKey(msgId)) {
            return null;
        }
        return registerResponseMsgPoolMap.get(msgId);
    }

    public static void main(String[] args) {

    }


}
