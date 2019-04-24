package com.netty.test.proto;


import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.GeneratedMessageV3;
import com.netty.test.annotation.ReqMapping;
import com.netty.test.common.cache.ClassCache;
import com.netty.test.pojo.proto.NettyTest;
import com.netty.test.server.ServerMessagePool;
import com.google.protobuf.GeneratedMessageV3.Builder;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：消息发送处理
 */
public class MessageProcessHelper {

    private static MessageProcessHelper instance = new MessageProcessHelper();

    public static MessageProcessHelper getInstance() {
        return instance;
    }

    public MessageProcessHelper() {
    }

    /**
     * request消息处理
     *
     * @param message
     */
    public void requestExecute(Message message) {

        int cmdId = message.getCmdId();
        Descriptors.Descriptor descriptor = ServerMessagePool.getInstance().getMsg(cmdId);

        try {
            DynamicMessage.Builder builder = DynamicMessage.parseFrom(descriptor, message.getBody()).toBuilder();
            System.out.println(builder);

            Map<Descriptors.FieldDescriptor, Object> allFields = builder.getAllFields();
            for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
                System.out.println(entry.getKey());
            }

            Set<Class<?>> reqMappingSet = ClassCache.REQ_MAPPING_SET;
            if (null == reqMappingSet) {
                return;
            }
            for (Class<?> aClass : reqMappingSet) {
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    ReqMapping methodAnnotation = method.getAnnotation(ReqMapping.class);
                    if (null == methodAnnotation) {
                        continue;
                    }
                    if (methodAnnotation.id() != 1) {
                        continue;
                    }
                    //获取形参
                    Type[] parameterTypes = method.getGenericParameterTypes();

                    if (parameterTypes.length < 1) {
                        method.invoke(aClass.newInstance());
                    } else {
                        method.invoke(aClass.newInstance(), builder);
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * response消息处理
     *
     * @param message
     */
    public void responseExecute(Object message) {

    }
}
