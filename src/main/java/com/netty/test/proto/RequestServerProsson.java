package com.netty.test.proto;


import com.netty.test.annotation.ReqMapping;
import com.netty.test.common.cache.ClassCache;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

public class RequestServerProsson {

    /**
     * todo 执行某个对象下的某个方法
     *
     * @param message
     */
    public void ss(Message message) {
        if (null == message) {
            return;
        }
        Set<Class<?>> reqMappingSet = ClassCache.reqMappingSet;
        try {
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
                        method.invoke(aClass.newInstance(), "");
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
