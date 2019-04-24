package com.netty.test.proto;


import com.google.protobuf.Descriptors;
import com.google.protobuf.DynamicMessage;
import com.netty.test.annotation.ReqMapping;
import com.netty.test.common.cache.ClassCache;
import com.netty.test.server.ServerMessagePool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

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
                System.out.println(entry.getValue());
            }

            Map<Integer, Class<?>> reqMappingMap = ClassCache.REQ_MAPPING_MAP;
            if (null == reqMappingMap) {
                return;
            }
            boolean b = reqMappingMap.containsKey(message.getModuleId());
            if (!b) {
                return;
            }
            Class<?> aClass = reqMappingMap.get(message.getModuleId());
            Method[] methods = aClass.getMethods();

            for (Method method : methods) {
                ReqMapping methodAnnotation = method.getAnnotation(ReqMapping.class);
                if (null == methodAnnotation) {
                    continue;
                }
                if (methodAnnotation.id() != message.getCmdId()) {
                    continue;
                }
                //获取形参
                Type[] parameterTypes = method.getGenericParameterTypes();

                if (parameterTypes.length < 1) {
                    method.invoke(aClass.newInstance());
                } else {
                    for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
//                        System.out.println(entry.getKey());
//                        System.out.println(entry.getValue());
                    }
                    for (Type type : parameterTypes) {
                        String typeName = type.getTypeName();
                        Class<?> typeClass = Class.forName(typeName);
                        Field[] fields = typeClass.getFields();

                        Object o = typeClass.newInstance();

                        for (Field field : fields) {
                            String name = field.getName();

                            Object o1 = allFields.get(name);
                            System.out.println(o1);

                            /*for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
                                System.out.println(entry.getKey());
                                System.out.println(entry.getValue());
                            }
                            field.setAccessible(true);
                            String s = "set" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                            Method[] methods1 = aClass.getMethods();

                            for (Method method1 : methods1) {
                                method1.invoke(o, o1);
                            }*/


                        }

                        Method[] typeClassMethods = typeClass.getMethods();
                        for (Method typeClassMethod : typeClassMethods) {
                            /*for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : allFields.entrySet()) {
                                System.out.println(entry.getKey());
                                System.out.println(entry.getValue());
                            }*/
                           /* String name = typeClassMethod.getName();
                            System.out.println(name);
                            String typeClassMethodName = typeClassMethod.getName();
                            System.out.println(typeClassMethodName);*/
                        }
//                        System.out.println(typeClass);
                        /*Method[] methods1 = type.getClass().getMethods();
                        for (Method method1 : methods1) {
                            String name = method1.getName();
                            System.out.println(name);
                        }*/

                    }

                    method.invoke(aClass.newInstance(), builder);
                }
                return;
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
