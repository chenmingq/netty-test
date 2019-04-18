package com.netty.test.consts;


/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description： 序列化类型常量
 */
public interface SerializableTypeConst {

    /**
     * 序列化类型
     */
    interface SerializableType {

        /**
         * jdk的serializable序列化类型
         */
        int JDK_SERIALIZABLE = 1;

        /**
         * ProtoStuff 序列化类型
         */
        int PROTO_STUFF_SERIALIZABLE = 2;
    }


}
