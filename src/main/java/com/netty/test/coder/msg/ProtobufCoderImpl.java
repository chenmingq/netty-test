package com.netty.test.coder.msg;

import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.test.server.ServerMessagePool;

import java.util.HashMap;
import java.util.Map;

public class ProtobufCoderImpl implements MsgCoder {

    @Override
    public Map<String, Object> prosson(int cmdId, byte[] body) {
        Map<String, Object> resultMap = new HashMap<>();
        Descriptors.Descriptor descriptor = ServerMessagePool.getInstance().getMsg(cmdId);
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
}
