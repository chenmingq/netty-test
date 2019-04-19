package com.netty.test.serializer;

import com.netty.test.coder.serializer.ProtoStuffUtils;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description： ProtoStuff 序列化处理
 */
public class ProtoStuffImpl implements SerializerProcess {

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) {
        return ProtoStuffUtils.deserializer(bytes, clazz);
    }

    @Override
    public byte[] serializer(Object val) {
        return ProtoStuffUtils.serializer(val);
    }
}
