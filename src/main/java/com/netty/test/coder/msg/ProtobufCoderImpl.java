package com.netty.test.coder.msg;

import com.google.protobuf.*;
import com.netty.test.pojo.proto.NettyTest;
import com.netty.test.proto.BaseMsg;
import com.netty.test.server.ServerMessagePool;

import java.util.HashMap;
import java.util.Map;

public class ProtobufCoderImpl implements MsgCoder {

    @Override
    public Map<String, Object> prossonRequest(int cmdId, byte[] body) {
        Map<String, Object> resultMap = new HashMap<>();
        Descriptors.Descriptor descriptor = ServerMessagePool.getInstance().getReqMsg(cmdId);
        DynamicMessage.Builder builder = null;
        try {
            builder = DynamicMessage.parseFrom(descriptor, body).toBuilder();
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        if (null == builder) {
            return null;
        }

        Map<Descriptors.FieldDescriptor, Object> allFields = builder.getAllFields();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
            resultMap.put(entry.getKey().getName(), entry.getValue());
        }
        return resultMap;
    }

    @Override
    public void prossonResponse(BaseMsg message) {
        GeneratedMessageV3 resMsg = ServerMessagePool.getInstance().getResMsg(message.getCmdId());
        if (null == resMsg) {
            return;
        }
        Message.Builder builder1 = resMsg.toBuilder();
        Descriptors.Descriptor descriptorForType = builder1.getDescriptorForType();
        System.out.println(descriptorForType);
        Map<Descriptors.FieldDescriptor, Object> allFields = builder1.getAllFields();
        System.out.println(allFields);

        System.out.println(resMsg);

        NettyTest.ResQueryTableData.Builder builder = NettyTest.ResQueryTableData.newBuilder();
        builder.setTableData("zhangsan");
        System.out.println(builder);

    }
}
