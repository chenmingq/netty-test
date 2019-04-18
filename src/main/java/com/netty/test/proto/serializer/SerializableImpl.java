package com.netty.test.proto.serializer;

import com.netty.test.proto.serializer.factory.SerializableUtils;

import java.io.Serializable;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description： Serializable 序列化处理
 */
public class SerializableImpl implements SerializerProcess {


    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) {
        return (T) SerializableUtils.deserializer(bytes);
    }

    @Override
    public byte[] serializer(Object val) {
        return SerializableUtils.serializer((Serializable) val);
    }
}
