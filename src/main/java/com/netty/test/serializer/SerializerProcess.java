package com.netty.test.serializer;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description：  序列化处理
 */
public interface SerializerProcess {


    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @return
     */
    <T> T deserializer(byte[] bytes, Class<T> clazz);

    /**
     * 序列化
     *
     * @param val
     * @return
     */
    byte[] serializer(Object val);

}
