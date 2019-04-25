package com.netty.test.proto;


import com.netty.test.annotation.ParamName;
import com.netty.test.annotation.ReqMapping;
import com.netty.test.coder.msg.MsgCoder;
import com.netty.test.common.cache.ClassCache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
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
        try {
            Map<Integer, Class<?>> reqMappingMap = ClassCache.REQ_MAPPING_MAP;
            if (null == reqMappingMap) {
                return;
            }
            boolean b = reqMappingMap.containsKey(message.getModuleId());
            if (!b) {
                return;
            }
            int cmdId = message.getCmdId();
            MsgCoder msgCoder = (MsgCoder) Class.forName(CommonConst.SERIALIZER_IMPL_CLASS).newInstance();
            Map<String, Object> valueMap = msgCoder.prosson(cmdId, message.getBody());
            if (null == valueMap) {
                return;
            }
            Class<?> aClass = reqMappingMap.get(message.getModuleId());
            Method[] methods = aClass.getMethods();
            Object newInstance = aClass.newInstance();

            for (Method method : methods) {
                ReqMapping methodAnnotation = method.getAnnotation(ReqMapping.class);
                if (null == methodAnnotation) {
                    continue;
                }
                if (methodAnnotation.id() != message.getCmdId()) {
                    continue;
                }

                //获取形参
                Parameter[] parameters = method.getParameters();

                if (parameters.length < 1) {
                    method.invoke(newInstance);
                } else {
                    List<Object> parameterTypeList = new LinkedList<>();

                    for (Parameter parameter : parameters) {
                        ParamName annotation = parameter.getAnnotation(ParamName.class);
                        if (null == annotation) {
                            Class<?> typeClass = parameter.getType();
                            Object o = typeClass.newInstance();

                            Field[] declaredFields = typeClass.getDeclaredFields();
                            for (Field declaredField : declaredFields) {
                                String fieldName = declaredField.getName();
                                if (!valueMap.containsKey(fieldName)) {
                                    continue;
                                }
                                declaredField.setAccessible(true);
                                declaredField.set(o, valueMap.get(fieldName));
                            }
                            parameterTypeList.add(o);
                        } else {
                            String param = annotation.name();
                            if (!valueMap.containsKey(param)) {
                                continue;
                            }
                            parameterTypeList.add(valueMap.get(param));
                        }
                    }
                    Object[] objects = new Object[parameterTypeList.size()];

                    for (int i = 0; i < parameterTypeList.size(); i++) {
                        objects[i] = parameterTypeList.get(i);
                    }
                    // todo 需要向下转型的处理
                    method.invoke(newInstance, objects);
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
