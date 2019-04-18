package com.netty.test.coder.serializer;

import com.netty.test.consts.SerializableTypeConst;
import com.netty.test.serializer.ProtoStuffImpl;
import com.netty.test.serializer.SerializableImpl;
import com.netty.test.serializer.SerializerProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description：序列化工厂
 */
public class SerializableFactory {

    private static Map<Integer, SerializerProcess> map = new HashMap<>();


    public <T> T deserializer(int type, byte[] bytes, Class<T> t) {
        if (!map.containsKey(type)) {
            throw new RuntimeException("反序列化操作失败");
        }
        return map.get(type).deserializer(bytes, t);
    }

    public byte[] serializer(int type, Object val) {
        if (!map.containsKey(type)) {
            throw new RuntimeException("序列化操作失败");
        }
        return map.get(type).serializer(val);
    }

    static {
        map.put(SerializableTypeConst.SerializableType.JDK_SERIALIZABLE, new SerializableImpl());
        map.put(SerializableTypeConst.SerializableType.PROTO_STUFF_SERIALIZABLE, new ProtoStuffImpl());
    }


}
