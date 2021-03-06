package com.netty.test.proto.serializer.factory;

import com.netty.test.proto.serializer.SerializeType;
import com.netty.test.proto.serializer.ProtoStuffImpl;
import com.netty.test.proto.serializer.SerializableImpl;
import com.netty.test.proto.serializer.SerializerProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description：序列化工厂
 */
public class SerializableFactory {

    private static Map<Byte, SerializerProcess> map = new HashMap<>();


    public <T> T deserializer(byte type, byte[] bytes, Class<T> t) {
        if (!map.containsKey(type)) {
            throw new RuntimeException("反序列化操作失败");
        }
        return map.get(type).deserializer(bytes, t);
    }

    public byte[] serializer(byte type, Object val) {
        if (!map.containsKey(type)) {
            throw new RuntimeException("序列化操作失败");
        }
        return map.get(type).serializer(val);
    }

    static {
        map.put(SerializeType.JDK_SERIALIZABLE.getType(), new SerializableImpl());
        map.put(SerializeType.PROTO_STUFF_SERIALIZABLE.getType(), new ProtoStuffImpl());
    }


}
