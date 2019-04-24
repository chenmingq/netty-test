package com.netty.test.coder.msg;

import com.google.protobuf.DescriptorProtos;
import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.test.proto.BaseMsg;
import com.netty.test.server.ServerMessagePool;

import java.lang.reflect.Field;
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
        Descriptors.Descriptor descriptor = ServerMessagePool.getInstance().getResMsg(message.getCmdId());
        if (null == descriptor) {
            return;
        }
        DescriptorProtos.DescriptorProto descriptorProto = descriptor.toProto();

        Field[] declaredFields = message.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            System.out.println(name);
        }
        Map<Descriptors.FieldDescriptor, Object> allFields = descriptorProto.getAllFields();


        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
            try {
                Field declaredField = message.getClass().getDeclaredField(entry.getKey().getName());
                if (null == declaredField) {
                    continue;
                }
                entry.setValue("你好");
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
