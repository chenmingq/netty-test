package com.netty.test.common.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoBufUtil {

    public ProtoBufUtil() {
    }

    /**
     * 序列化
     *
     * @param o
     * @param <T>
     * @return
     */
    public static <T> byte[] serializer(T o) {
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(o.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] bytes = null;
        try {
            bytes = ProtobufIOUtil.toByteArray(o, schema, LinkedBuffer.allocate(1024 * 1024));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            buffer.clear();
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {

        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema<T> schema = RuntimeSchema.getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
