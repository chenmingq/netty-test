package com.netty.test.coder.msg;

import com.google.protobuf.*;
import com.netty.test.pojo.proto.NettyTest;
import com.netty.test.proto.BaseMsg;
import com.netty.test.server.ServerMessagePool;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
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

        Descriptors.FileDescriptor file = descriptor.getFile();
        DescriptorProtos.FileDescriptorProto fileDescriptorProto = file.toProto();

        List<Descriptors.Descriptor> messageTypes = file.getMessageTypes();
//        System.out.println(messageTypes);
        List<DescriptorProtos.DescriptorProto> messageTypeList = fileDescriptorProto.getMessageTypeList();
        for (DescriptorProtos.DescriptorProto proto : messageTypeList) {
            List<DescriptorProtos.FieldDescriptorProto> fieldList = proto.getFieldList();
            for (DescriptorProtos.FieldDescriptorProto descriptorProto : fieldList) {
                System.out.println(descriptorProto);
            }
        }

//        System.out.println(allFields1);
//
//        System.out.println(fileDescriptorProto);


        DynamicMessage.Builder builder = DynamicMessage.newBuilder(descriptor);


        DynamicMessage.Builder builder1 = builder.buildPartial().toBuilder();

        Map<Descriptors.FieldDescriptor, Object> allFields = builder1.getAllFields();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
            System.out.println(entry.getKey());
        }


        DescriptorProtos.DescriptorProto descriptorProto = descriptor.toProto();

        List<DescriptorProtos.FieldDescriptorProto> fieldList = descriptorProto.getFieldList();

        /*NettyTest.ResQueryTableData.Builder f =         NettyTest.ResQueryTableData.newBuilder();

        f.setTableData("你好啊啊");

        System.out.println(f);*/


        Field[] declaredFields = message.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            declaredField.setAccessible(true);
            for (DescriptorProtos.FieldDescriptorProto proto : fieldList) {
                String protoName = proto.getName();
                if (!protoName.equals(name)) {
                    continue;
                }
                System.out.println(protoName);
                try {
                    Object value = declaredField.get(message);
                    System.out.println(value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
