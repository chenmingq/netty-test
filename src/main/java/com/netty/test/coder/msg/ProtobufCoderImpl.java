package com.netty.test.coder.msg;

import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.netty.test.proto.BaseMsg;
import com.netty.test.proto.CommonConst;
import com.netty.test.server.ServerMessagePool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ProtobufCoderImpl implements MsgCoder {

    @Override
    public Map<String, Object> prossonRequest(int cmdId, byte[] body) {
        Map<String, Object> resultMap = new HashMap<>();
        Descriptors.Descriptor descriptor = ServerMessagePool.getInstance().getReqMsg(cmdId);
        if (null == descriptor) {
            return null;
        }
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
        Message resMsg = ServerMessagePool.getInstance().getResMsg(message.getCmdId());
        if (null == resMsg) {
            return;
        }
        Message.Builder msgBuilder = resMsg.toBuilder();
        Descriptors.Descriptor descriptorForType = msgBuilder.getDescriptorForType();

        Class<? extends BaseMsg> messageClass = message.getClass();
        Field[] declaredFields = messageClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            Descriptors.FieldDescriptor fieldByName = descriptorForType.findFieldByName(name);
            if (null == fieldByName) {
                continue;
            }
            declaredField.setAccessible(true);
            try {
                Object msgValue = declaredField.get(message);
                msgBuilder.setField(fieldByName, msgValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = msgBuilder.build().toByteArray();

        com.netty.test.proto.Message msg = new com.netty.test.proto.Message();
        msg.setCmdId(message.getCmdId());
        msg.setModuleId(message.getModuleId());
        msg.setBody(bytes);
        msg.setMagic(CommonConst.MAGIC_CODE);

        // todo 发送消息 xxx.sendMsg(key,msg);
    }
}
